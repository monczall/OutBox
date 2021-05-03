package main.java.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Areas.class)
public abstract class Areas_ {

	public static volatile SingularAttribute<Areas, String> city;
	public static volatile SingularAttribute<Areas, String> departmentStreetAndNumber;
	public static volatile SingularAttribute<Areas, String> name;
	public static volatile SingularAttribute<Areas, String> voivodeship;
	public static volatile CollectionAttribute<Areas, Users> usersById;
	public static volatile SingularAttribute<Areas, Integer> id;

	public static final String CITY = "city";
	public static final String DEPARTMENT_STREET_AND_NUMBER = "departmentStreetAndNumber";
	public static final String NAME = "name";
	public static final String VOIVODESHIP = "voivodeship";
	public static final String USERS_BY_ID = "usersById";
	public static final String ID = "id";

}

