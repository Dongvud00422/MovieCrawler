package com.Crawler.model;

import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;
public class ObjectifyModel {
    private Logger LOG = Logger.getLogger(ObjectifyModel.class.getSimpleName());
    public boolean insertEntities(List<Object> obj){
        if (ofy().save().entities(obj).now() != null){
            System.out.println(obj.getClass().getSimpleName()+"Insert ok");
            LOG.info(obj.getClass().getSimpleName()+"Insert success");
            return true;
        }
        LOG.severe(obj.getClass().getSimpleName()+"Insert error");
        return false;
    }
}
