package main.java.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserInfos.class)
public abstract class UserInfos_ {

	public static volatile SingularAttribute<UserInfos, String> streetAndNumber;
	public static volatile SingularAttribute<UserInfos, String> phoneNumber;
	public static volatile SingularAttribute<UserInfos, String> city;
	public static volatile SingularAttribute<UserInfos, String> surname;
	public static volatile SingularAttribute<UserInfos, String> name;
	public static volatile SingularAttribute<UserInfos, String> voivodeship;
	public static volatile CollectionAttribute<UserInfos, Users> usersById;
	public static volatile SingularAttribute<UserInfos, Integer> id;
	public static volatile CollectionAttribute<UserInfos, Packages> packagesById;

	public static final String STREET_AND_NUMBER = "streetAndNumber";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String CITY = "city";
	public static final String SURNAME = "surname";
	public static final String NAME = "name";
	public static final String VOIVODESHIP = "voivodeship";
	public static final String USERS_BY_ID = "usersById";
	public static final String ID = "id";
	public static final String PACKAGES_BY_ID = "packagesById";

}

