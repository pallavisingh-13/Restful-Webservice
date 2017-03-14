package com.user.provider;

import com.user.UserHelper;
import com.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.cassandra.core.CassandraOperations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;


@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class UserProviderTest {

    @Mock
    CassandraOperations cassandraOperations;

    @InjectMocks
    UserProvider userProvider;

    @Test
    public void getUserInfoTest() {

        doReturn(UserHelper.getMockUserList()).when(cassandraOperations)
                .select(any(String.class), any(Class.class));
        List<User> userList = userProvider
                .getUserInfo("10");
        assertNotNull(userList);
        assertEquals(userList.get(0).getId(), "10");
    }
}