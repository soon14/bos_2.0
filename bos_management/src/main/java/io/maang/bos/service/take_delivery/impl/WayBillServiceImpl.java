package io.maang.bos.service.take_delivery.impl;

import io.maang.bos.dao.take_delivery.WayBillRepository;
import io.maang.bos.domain.take_delivery.WayBill;
import io.maang.bos.index.WayBillIndexRepository;
import io.maang.bos.service.take_delivery.WayBillService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-04-19 15:49
 */
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {

    @Autowired
    private WayBillRepository wayBillRepository;

    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    @Override
    public void save(WayBill wayBill) {
        //判断运单是否存在
        WayBill persistWayBill = wayBillRepository.findByWayBillNum(wayBill.getWayBillNum());
        if (persistWayBill == null || persistWayBill.getId() == null) {
            //运单不存在
            wayBillRepository.save(wayBill);
            //保持索引
            wayBillIndexRepository.save(wayBill);

        } else {
            try {
                //运单存在
                Integer id = persistWayBill.getId();
                BeanUtils.copyProperties(persistWayBill,wayBill);
                persistWayBill.setId(id);

                //保持索引
                wayBillIndexRepository.save(persistWayBill);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public Page<WayBill> findPageData(Pageable pageable) {
        return wayBillRepository.findAll(pageable);
    }

    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        return wayBillRepository.findByWayBillNum(wayBillNum);
    }

    @Override
    public Page<WayBill> findPageData(WayBill wayBill, Pageable pageable) {
        //1.判断WayBill中条件是否存在
        if (StringUtils.isBlank(wayBill.getWayBillNum())
                && StringUtils.isBlank(wayBill.getSendAddress())
                && StringUtils.isBlank(wayBill.getRecAddress())
                && StringUtils.isBlank(wayBill.getSendProNum())
                && (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)) {
            //无条件查询,数据库
            return wayBillRepository.findAll(pageable);
        } else {
            //查询条件
            //must条件必须成立 and
            //must not 条件必须不成立 not
            //should 条件可以成立 or
            BoolQueryBuilder query = new BoolQueryBuilder(); //布尔查询,多条件组合查询
            //向组合对象添加条件
            if (StringUtils.isNoneBlank(wayBill.getWayBillNum())) {
                //运单号查询
                QueryBuilder termQuery = new TermQueryBuilder("wayBillNum", wayBill.getWayBillNum());
                query.must(termQuery);
            }
            if (StringUtils.isNoneBlank(wayBill.getSendAddress())) {
                //发货地模糊查询
                //情况一:输入"北"是查询词条的一部分,使用模糊匹配词条查询
                QueryBuilder wildcardQuery = new WildcardQueryBuilder("sendAddress", "*" + wayBill.getSendAddress() + "*");
                //情况二:输入"北京市海淀区"是多个词条组合,进行分词后进行每个分词的匹配查询
                QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendAddress())
                        .field("sendAddress")
                        .defaultOperator(QueryStringQueryBuilder.Operator.AND);
                //两种情况取or
                BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
                boolQueryBuilder.should(wildcardQuery);
                boolQueryBuilder.should(queryStringQueryBuilder);
                query.must(boolQueryBuilder);
            }
            if (StringUtils.isNoneBlank(wayBill.getRecAddress())) {
                //收货地模糊查询
                QueryBuilder wildcardQuery = new WildcardQueryBuilder("recAddress", "*" + wayBill.getRecAddress() + "*");
                query.must(wildcardQuery);
            }
            if (StringUtils.isNoneBlank(wayBill.getSendProNum())) {
                //速运类型的等值查询
                QueryBuilder termQuery = new TermQueryBuilder("sendProNum", wayBill.getSendProNum());
                query.must(termQuery);
            }
            if (wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0) {
                //签收状态的查询
                QueryBuilder termQuery = new TermQueryBuilder("signStatus", wayBill.getSignStatus());
                query.must(termQuery);
            }
            SearchQuery searchQuery = new NativeSearchQuery(query);
            searchQuery.setPageable(pageable); //分页的效果
            //有条件的查询,查询索引库
            return wayBillIndexRepository.search(searchQuery);
        }
    }
}