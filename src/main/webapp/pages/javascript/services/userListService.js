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

/**
 * Created by Wiker on 15/04/16.
 */


'use strict';

angular.module('mdbApp.services')
    .service('UserListService', function ($resource) {
        return {
            getUsers: function () {
                return $resource("/api/list", {}, {
                    get: {
                        isArray: true,
                        method: "GET",
                        cache: true
                    }
                });
            },
            getGroups: function(id){
                return $resource("/api/"+id, {}, {
                        get:{
                            isArray:false,
                            method:"GET"
                        }
                    }
                );
            }
        }
    });