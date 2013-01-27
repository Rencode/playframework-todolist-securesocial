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
package controllers;

import models.Task;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;
import views.html.index;

/**
 * A sample controller
 */
public class Application extends Controller {
	static Form<Task> taskForm = form(Task.class);

	/**
	 * This action only gets called if the user is logged in.
	 * 
	 * @return
	 */
	public static Result index() {
		return redirect(routes.Application.tasks());
	}

	@SecureSocial.SecuredAction
	public static Result tasks() {
		Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);
		return ok(index.render(user, Task.all(user), taskForm));
	}

	@SecureSocial.SecuredAction
	public static Result deleteTask(Long id) {
		Task.delete(id);
		return redirect(routes.Application.tasks());
	}

	@SecureSocial.SecuredAction
	public static Result newTask() {
		Form<Task> filledForm = taskForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			// Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);
			// return badRequest(views.html.index.render(Task.all(user),
			// filledForm));
			return badRequest("form had errors");
		} else {
			Identity user = (Identity) ctx().args.get(SecureSocial.USER_KEY);
			Task.create(filledForm.get(), user);
			return redirect(routes.Application.tasks());
		}
	}
}
