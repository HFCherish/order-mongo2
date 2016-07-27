package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.Map;

import static com.thoughtworks.ketsu.support.TestHelper.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(ApiTestRunner.class)
public class UsersApiTest extends ApiSupport {
    private String userBaseUrl = "/users";

    @Inject
    UserRepository userRepository;

    @Test
    public void should_register() {
        Response response = post(userBaseUrl, userJsonForTest(USER_NAME));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString(), containsString(userBaseUrl));
        assertThat(response.getLocation().toString().matches(".*/[a-zA-Z\\d]+$"), is(true));

    }

    @Test
    public void should_400_when_register_with_invalid_name() {
        Response response = post(userBaseUrl, userJsonForTest(INVALID_USER_NAME));

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_get_one_user() {
        User user = prepareUser(userRepository);

        Response response = get(userBaseUrl + "/" + user.getId());

        assertThat(response.getStatus(), is(200));
        Map info = response.readEntity(Map.class);
        assertThat(info.get("uri").toString(), containsString(userBaseUrl + "/" + user.getId()));
        assertThat(info.get("_id"), is(user.getId()));
        assertThat(info.get("name"), is(user.getName()));

    }

    @Test
    public void should_404_when_get_given_not_exists() {
        Response response = get(userBaseUrl + "/" + new ObjectId());

        assertThat(response.getStatus(), is(404));

    }
}
