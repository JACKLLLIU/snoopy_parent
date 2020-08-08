package com.bilibili.commonUtils;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResultMessage {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data = new HashMap<String,Object>();

    //让构造私有化，让别人只能调用自己的静态方法来使用该实例
    private ResultMessage(){};

    //成功的静态方法
    public static ResultMessage ok(){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setSuccess(true);
        resultMessage.setCode(ResultCode.SUCCESS);
        resultMessage.setMessage("成功");
        return resultMessage;
    }

    public static ResultMessage error(){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setSuccess(false);
        resultMessage.setCode(ResultCode.ERROR);
        resultMessage.setMessage("失败");
        return resultMessage;
    }

    public ResultMessage success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResultMessage message(String message){
        this.setMessage(message);
        return this;
    }


    public ResultMessage code(Integer code){
        this.setCode(code);
        return this;
    }


    public ResultMessage data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public ResultMessage data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
