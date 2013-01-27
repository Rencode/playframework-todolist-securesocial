package models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.h2.engine.User;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import scala.Option;
import scala.Some;
import securesocial.core.AuthenticationMethod;
import securesocial.core.Identity;
import securesocial.core.OAuth1Info;
import securesocial.core.OAuth2Info;
import securesocial.core.PasswordInfo;
import securesocial.core.UserId;

@Entity
public class AppUser extends Model implements Identity {

	private static final long serialVersionUID = 8178805141915775112L;

	@Id
	public Long id;

	public String userId;

	public String providerId;

	public String firstName;

	public String lastName;

	public String avatarUrl;

	public String email;

	public String fullName;

	public String hasher;

	public String password;

	public String salt;

	// @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
	// public Set<Task> task;

	public static Finder<Long, AppUser> find = new Finder(Long.class,
			AppUser.class);

	public static AppUser find(UserId userId) {
		return find.where().eq("userId", userId.id())
				.eq("providerId", userId.providerId()).findUnique();
	}

	public AppUser(final Identity user) {
		this.userId = user.id().id();
		this.providerId = user.id().providerId();
		this.avatarUrl = user.avatarUrl().toString();
		this.email = user.email().toString();
		this.firstName = user.firstName();
		this.fullName = user.fullName();
		this.lastName = user.lastName();

		final PasswordInfo passwordInfo = user.passwordInfo().get();
		this.password = passwordInfo.password();
		this.salt = passwordInfo.salt().toString();
		this.hasher = passwordInfo.hasher();
	}

	public static AppUser findByEmailAndProvider(final String email,
			final String providerId) {
		return find.where().eq("email", email).eq("providerId", providerId)
				.findUnique();
	}

	@Override
	public AuthenticationMethod authMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Option<String> avatarUrl() {
		return new Some<String>(avatarUrl);
	}

	@Override
	public Option<String> email() {
		return new Some<String>(email);
	}

	@Override
	public String firstName() {
		return firstName;
	}

	@Override
	public String fullName() {
		return fullName;
	}

	@Override
	public UserId id() {
		return new UserId(String.valueOf(id), providerId);
	}

	@Override
	public String lastName() {
		return lastName;
	}

	@Override
	public Option<OAuth1Info> oAuth1Info() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Option<OAuth2Info> oAuth2Info() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Option<PasswordInfo> passwordInfo() {
		final PasswordInfo passwordInfo = new PasswordInfo(hasher, password,
				new Some<String>(salt));
		return new Some<PasswordInfo>(passwordInfo);
	}

}
