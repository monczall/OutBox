package main.java.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PackageType.class)
public abstract class PackageType_ {

	public static volatile SingularAttribute<PackageType, String> sizeName;
	public static volatile SingularAttribute<PackageType, String> size;
	public static volatile SingularAttribute<PackageType, String> price;
	public static volatile SingularAttribute<PackageType, String> weight;
	public static volatile SingularAttribute<PackageType, Integer> id;
	public static volatile CollectionAttribute<PackageType, Packages> packagesById;

	public static final String SIZE_NAME = "sizeName";
	public static final String SIZE = "size";
	public static final String PRICE = "price";
	public static final String WEIGHT = "weight";
	public static final String ID = "id";
	public static final String PACKAGES_BY_ID = "packagesById";

}

