package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import securesocial.core.Identity;

@Entity
public class Task extends Model {

	@Id
	public Long id;

	@Required
	public String label;

	@ManyToOne
	public AppUser appUser;

	public static Finder<Long, Task> find = new Finder(Long.class, Task.class);

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static List<Task> all(final Identity user) {
		return find.where().eq("appUser", user).findList();
	}

	public static void create(Task task, final Identity appUser) {
		task.appUser = (AppUser) appUser;
		task.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}
}