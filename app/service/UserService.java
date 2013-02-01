/**
 * Copyright 2012 Jorge Aliss (jaliss at gmail dot com) - twitter: @jaliss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import models.AppUser;

import play.Application;
import scala.Option;
import securesocial.core.Identity;
import securesocial.core.SocialUser;
import securesocial.core.UserId;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.Token;

/**
 * A Sample In Memory user service in Java
 * 
 * Note: This is NOT suitable for a production environment and is provided only
 * as a guide. A real implementation would persist things in a database
 */
public class UserService extends BaseUserService {
	private final HashMap<String, Identity> users = new HashMap<String, Identity>();
	private final HashMap<String, Token> tokens = new HashMap<String, Token>();

	public UserService(Application application) {
		super(application);
	}

	@Override
	public void doSave(Identity user) {
		AppUser.create(user);
	}

	@Override
	public void doSave(Token token) {
		tokens.put(token.uuid, token);
	}

	@Override
	public Identity doFind(UserId userId) {
		return AppUser.find(userId);
	}

	@Override
	public Token doFindToken(String tokenId) {
		return tokens.get(tokenId);
	}

	@Override
	public Identity doFindByEmailAndProvider(String email, String providerId) {
		return AppUser.findByEmailAndProvider(email, providerId);
	}

	@Override
	public void doDeleteToken(String uuid) {
		tokens.remove(uuid);
	}

	@Override
	public void doDeleteExpiredTokens() {
		Iterator<Map.Entry<String, Token>> iterator = tokens.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Token> entry = iterator.next();
			if (entry.getValue().isExpired()) {
				iterator.remove();
			}
		}
	}
}
