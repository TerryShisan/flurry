package com.plans.rest;

import com.plans.common.BaseResult;
import com.plans.common.ResultCode;
import com.plans.configure.MqSend;
import com.plans.bean.Customer;
import com.plans.dao.CustomerRepository;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by river on 2016/4/2.
 */
@Component
@Path("/journey")
public class Journey{
    @Autowired
    @Qualifier("kafkaProducer")
    MqSend mqSend;
    @Autowired
    CustomerRepository journeyDao;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    //ToDo 因为尝试用JSON的方式接收POST请求带过来的数据失败，这里用String类型的变量接收消息
    public BaseResult addJourney(String plane)throws JSONException{
        BaseResult result = new BaseResult();
        JSONObject json= new JSONObject(plane);
        System.out.println(json.toString());
        if (json.has("type")== false|| ((String)json.get("type")).equals("")){
            result.setError(ResultCode.ILLEGAL_PARAM, "type must exist", "type", "");
            return result;
        }
        if (json.has("name")== false || ((String)json.get("name")).equals("")){
            result.setError(ResultCode.ILLEGAL_PARAM, "name must exist", "name","");
            return result;
        }
        if (json.has("date")== false|| ((String)json.get("date")).equals("")){
            result.setError(ResultCode.ILLEGAL_PARAM, "date must exist", "date","");
            return result;
        }

        System.out.println("Send kafka message.");
        //ToDO 此处需要看看如何确认发送成功，如果发送不成功应该如何处理。这个是kafka的处理机制。
        mqSend.send(plane);
        System.out.println("Send kafka message over.");
        return result;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> GetJourneys(@QueryParam("type") String type,
                                @QueryParam("name") String name,
                                @QueryParam("date") String date){
        List<Customer> journey = journeyDao.findAllJourneys();
        return journey;
    }

    //ToDo 目前还没有针对不同的关键字来获取行程信息
}
