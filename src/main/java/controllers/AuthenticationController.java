/**
 * Copyright (C) 2012 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import services.AuthenticationService;

@Singleton
public class AuthenticationController {

    @Inject
    AuthenticationService authenticationService;

    ///////////////////////////////////////////////////////////////////////////
    // Login
    ///////////////////////////////////////////////////////////////////////////
    public Result login(Context context) {
        return Results.html();
    }

    public Result loginPost(@Param("username") String username, @Param("password") String password, Context context) {
        return authenticationService.login(username, password, context, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Logout
    ///////////////////////////////////////////////////////////////////////////
    public Result logout(Context context) {
        // remove any user dependent information
        context.getSessionCookie().clear();
        context.getFlashCookie().success("login.logoutSuccessful");

        return Results.redirect("/login");
    }

    ///////////////////////////////////////////////////////////////////////////
    // Register
    ///////////////////////////////////////////////////////////////////////////
    public Result register(Context context) {
        return Results.html();
    }

    public Result registerPost(@Param("fullname") String fullname,
                               @Param("username") String username,
                               @Param("password") String password,
                               Context context) {

        return authenticationService.register(fullname, username, password, context);
    }
}
