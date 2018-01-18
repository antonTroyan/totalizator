package by.troyan.web.service.factory;

import by.troyan.web.service.*;
import by.troyan.web.service.implementation.*;

/**
 * ServiceFactory.Singleton to work with service options.
 */

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance(){
        return instance;
    }

    private ServiceFactory(){}

    public UserService getUserService(){
        return UserServiceImpl.getInstance();
    }

    public EventService getEventService(){
        return EventServiceImpl.getInstance();
    }

    public LeagueService getLeagueService(){
        return LeagueServiceImpl.getInstance();
    }

    public MemberService getMemberService(){
        return MemberServiceImpl.getInstance();
    }

    public RateService getRateService(){
        return RateServiceImpl.getInstance();
    }

    public PaySystemService getPaySystemService() {
        return PaySystemServiceImpl.getInstance();
    }

    public EventResultService getEventResultService() {
        return EventResultServiceImpl.getInstance();
    }

    public CategoryService getCategoryService() {
        return CategoryServiceImpl.getInstance();
    }
}
