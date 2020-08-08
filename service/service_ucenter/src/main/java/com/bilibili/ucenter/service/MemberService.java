package com.bilibili.ucenter.service;

import com.bilibili.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.ucenter.entity.vo.LoginVo;
import com.bilibili.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author LJF
 * @since 2020-08-04
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);
}
