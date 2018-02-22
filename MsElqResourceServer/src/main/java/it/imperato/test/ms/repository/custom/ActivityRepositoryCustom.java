package it.imperato.test.ms.repository.custom;

import it.imperato.test.ms.model.entities.Activity;

import java.util.List;

public interface ActivityRepositoryCustom {

    List<Activity> customGetSomeActivity(String assetType);

}
