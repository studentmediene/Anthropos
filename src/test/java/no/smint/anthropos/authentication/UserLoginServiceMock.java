/*
 * Copyright 2016 Studentmediene i Trondheim AS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.smint.anthropos.authentication;

import no.smint.anthropos.model.Person;

public class UserLoginServiceMock implements UserLoginService {
    @Override
    public int getId() {
        return 1;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean login(LdapUserPwd token) {
        return false;
    }

    @Override
    public void logout() {

    }

    @Override
    public Person getLoggedInUser() {
        return null;
    }
}
