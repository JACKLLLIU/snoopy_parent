package com.bilibili.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bilibili.commonUtils.JwtUtils;
import com.bilibili.commonUtils.MD5;
import com.bilibili.snoopy.exceptionhandler.MyException;
import com.bilibili.ucenter.entity.Member;
import com.bilibili.ucenter.entity.vo.LoginVo;
import com.bilibili.ucenter.entity.vo.RegisterVo;
import com.bilibili.ucenter.mapper.MemberMapper;
import com.bilibili.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author LJF
 * @since 2020-08-04
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    //短信发送的后，存储了<电话,验证码>
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        //拿到用户对象的数据，查询是否存在且生效
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ){
            throw new MyException(20001,"登录失败！");
        }

        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        //手机号就是唯一标识，我们将查询出来的数据，与传输过来的数据做校验
        Member member = baseMapper.selectOne(wrapper);

        //校验
        if(!mobile.equals(member.getMobile())){
            throw new MyException(20001,"登录异常！");
        }

        //校验密码
        //数据库通常不储存明文密码，保存的都是加密的，所以在密码的校验时需要用到加密解密
        //这里用到的是md5，进行加密后对数据库中的数据进行比较
        if(!MD5.encrypt(password).equals(member.getPassword())){
            throw new MyException(20001,"登录异常！");
        }


        //是否失效
        if(member.getIsDisabled() || member.getIsDeleted()){
            throw new MyException(20001,"登录异常！");
        }

        //登录校验成功，返回token
        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //先校验非空
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)){
            throw new MyException(20001,"注册异常");
        }
        //code检验，与redis中的数据经行对比
        String rediscode = redisTemplate.opsForValue().get(registerVo.getMobile());
        if(!rediscode.equals(code)){
            throw new MyException(20001,"验证码异常");
        }

        //校验数据库中手机号是否重复注册
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new MyException(20001,"同一手机号不能重复注册");
        }

        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/11/08/e44a2e92-2421-4ea3-bb49-46f2ec96ef88.png");
        baseMapper.insert(member);
    }
}
