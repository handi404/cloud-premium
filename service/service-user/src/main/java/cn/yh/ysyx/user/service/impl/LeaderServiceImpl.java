package cn.yh.ysyx.user.service.impl;

import cn.yh.ysyx.model.user.Leader;
import cn.yh.ysyx.user.mapper.LeaderMapper;
import cn.yh.ysyx.user.service.LeaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LeaderServiceImpl extends ServiceImpl<LeaderMapper, Leader> implements LeaderService {
}
