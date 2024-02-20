    package com.pkh.controller;

    import com.alibaba.fastjson.JSONObject;
    import com.pkh.annotation.DS;
    import com.pkh.bean.param.AdminListParam;
    import com.pkh.bean.response.PikaResponse;
    import com.pkh.dao.po.admindb.AdminUser;
    import com.pkh.service.AdminService;
    import com.pkh.util.RedisUtil;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import javax.annotation.Resource;
    import java.util.List;

    @Slf4j
    @RequestMapping("/admin")
    @RestController
    public class AdminController {
        @Resource
        AdminService adminService;

        @Resource
        RedisUtil redisUtil;
        @PostMapping("/list")
        @DS("admin")
        public PikaResponse list(AdminListParam param) {
            try {
                Integer total = adminService.countByParam(param);
                log.info("/admin/list, count number: {}", total);
                List<AdminUser> adminList = null;
                if (total > 0) {
                    adminList = adminService.getByParam(param);
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("total", total);
                jsonObject.put("list", adminList);
                return new PikaResponse(jsonObject);
            } catch (Exception e) {
                log.info("/admin/list, failed! exception:{} ", e.getMessage());
                return new PikaResponse("1", e.getMessage());
            }
        }
    }
