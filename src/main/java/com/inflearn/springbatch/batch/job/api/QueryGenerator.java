package com.inflearn.springbatch.batch.job.api;

import com.inflearn.springbatch.batch.domain.ProductVO;
import com.inflearn.springbatch.batch.rowmapper.ProductRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class QueryGenerator {

    public static ProductVO[] getProductList(DataSource dataSource) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<ProductVO> productList = jdbcTemplate.query("select type as type from product group by type", new ProductRowMapper() {
            @Override
            public ProductVO mapRow(ResultSet rs, int i) throws SQLException {
                return ProductVO.builder().type(rs.getString("type")).build();
            }
        });

        return productList.toArray(new ProductVO[]{});
    }

    public static Map<String, Object> getParameterForQuery(String parameter, String value) {

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(parameter, value);
        return parameters;
    }
}