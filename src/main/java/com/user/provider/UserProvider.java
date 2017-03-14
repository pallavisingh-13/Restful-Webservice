package com.user.provider;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.user.entity.User;
import com.user.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

@Slf4j
@Component
public class UserProvider {

    private static final String COMMA = ",";
    private static final String USER_TABLE = "user";
    private static final String USER_ID = "id";

    @Autowired
    private CassandraOperations cassandraOperations;

    public List<User> getUserInfo(String userIds) {
        List<User> userList = new ArrayList<>();
        List<Statement> selectQueryList = new ArrayList<>();
        for (String userId : Arrays.asList(userIds.split(COMMA))) {
            Statement statementForUser = QueryBuilder
                    .select()
                    .all()
                    .from(USER_TABLE)
                    .where(eq(USER_ID, userId))
                    .limit(1);
            log.debug("SKU price Query to fetch prices for items - [{}]",
                    statementForUser.toString());
            selectQueryList.add(statementForUser);
        }
        try {
            if (selectQueryList.size() > 0) {
                selectQueryList.stream().forEach(statement -> {
                    List<User> users = cassandraOperations
                            .select(statement.toString(), User.class);
                    if (null != users && !users.isEmpty()) {
                        userList.addAll(users);
                    }
                });
            }
        } catch (Exception exp) {
            log.error("USER : Exception occurred while fetching data from cassandra "
                    + "for userIds - [{}] - [{}]", userIds, exp);
            throw new UserException("User Exception while reading from Cassandra");
        }
        return userList;
    }

    public User createOrUpdateUser(User user) {
        try {
            return cassandraOperations.insert(user);
        } catch (Exception exp) {
            log.error("Exception occurred while adding the user" + exp);
            throw new UserException("Exception while adding." + exp);
        }
    }
    public void removeUser(String id) {
        try {
            Delete.Where deleteQuery = QueryBuilder.delete()
                    .from("user")
                    .where(eq("id", id));
            Batch batch = QueryBuilder.batch();
            batch.add(deleteQuery);
            cassandraOperations.execute(batch);
        } catch (Exception exp) {
            log.error("Exception occurred while deleting the user" + exp);
            throw new UserException("Exception while deleting." + exp);
        }
    }
}
