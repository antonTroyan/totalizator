package by.troyan.web.command.factory;

import by.troyan.web.command.CommandEnum;
import by.troyan.web.command.ICommand;
import by.troyan.web.command.implementation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Command fabric, singleton. <p>
 * Contains command map with keys - enums and values - objects.
 * Have one method to receive command from the factory.
 */

public class CommandFactory {
    private static final CommandFactory factory = new CommandFactory();
    public static CommandFactory getFactory(){
        return factory;
    }

    private Map<CommandEnum, ICommand> commands;

    private CommandFactory(){
        commands = new HashMap<>();
        commands.put(CommandEnum.SHOW_MAIN_PAGE, new ShowMainPageCommand());
        commands.put(CommandEnum.SHOW_NEAREST_EVENTS_PAGE_COMMAND, new ShowNearestEventsPageCommand());
        commands.put(CommandEnum.SHOW_CATEGORY_PAGE, new ShowCategoryPageCommand());
        commands.put(CommandEnum.SHOW_REGISTRATION_PAGE, new ShowRegistrationPageCommand());
        commands.put(CommandEnum.REGISTER, new RegisterCommand());
        commands.put(CommandEnum.LOGIN, new LoginCommand());
        commands.put(CommandEnum.SHOW_LOGIN_PAGE, new ShowLoginPageCommand());
        commands.put(CommandEnum.ADD_CATEGORIES_TO_REQUEST, new AddCategoriesToRequestCommand());
        commands.put(CommandEnum.LOGOUT, new LogoutCommand());
        commands.put(CommandEnum.CHANGE_LOCALE, new ChangeLocaleCommand());
        commands.put(CommandEnum.SHOW_RESULTS_PAGE, new ShowResultsPageCommand());
        commands.put(CommandEnum.SHOW_EVENT_PAGE, new ShowEventPageCommand());
        commands.put(CommandEnum.SHOW_ADD_EVENT_PAGE, new ShowAddEventPageCommand());
        commands.put(CommandEnum.GET_LEAGUES_BY_CATEGORY_JSON, new GetLeaguesByCategoryJsonCommand());
        commands.put(CommandEnum.GET_MEMBERS_BY_LEAGUE_JSON, new GetMembersByLeagueJsonCommand());
        commands.put(CommandEnum.ADD_EVENT, new AddEventCommand());
        commands.put(CommandEnum.SHOW_MAKE_RATE_PAGE, new ShowMakeRatePageCommand());
        commands.put(CommandEnum.SHOW_PERSONAL_PAGE, new ShowPersonalPageCommand());
        commands.put(CommandEnum.SHOW_FILL_UP_BALANCE_PAGE, new ShowFillUpPageCommand());
        commands.put(CommandEnum.SHOW_WITHDRAW_MONEY_PAGE, new ShowWithdrawMoneyPageCommand());
        commands.put(CommandEnum.FILL_UP_BALANCE, new FillUpBalanceCommand());
        commands.put(CommandEnum.WITHDRAW_MONEY, new WithdrawMoneyCommand());
        commands.put(CommandEnum.GET_MEMBERS_BY_EVENT_JSON, new GetMembersByEventJsonCommand());
        commands.put(CommandEnum.MAKE_RATE, new MakeRateCommand());
        commands.put(CommandEnum.SHOW_ADD_CATEGORY_PAGE, new ShowAddCategoryPageCommand());
        commands.put(CommandEnum.ADD_CATEGORY, new AddCategoryCommand());
        commands.put(CommandEnum.ADD_LEAGUE, new AddLeagueCommand());
        commands.put(CommandEnum.ADD_MEMBER, new AddMemberCommand());
        commands.put(CommandEnum.SHOW_ADD_MEMBER_PAGE, new ShowAddMemberPageCommand());
        commands.put(CommandEnum.SHOW_ADD_LEAGUE_PAGE, new ShowAddLeaguePageCommand());
        commands.put(CommandEnum.SHOW_ADMIN_PAGE, new ShowAdminPageCommand());
        commands.put(CommandEnum.BAN, new BanCommand());
        commands.put(CommandEnum.UNBAN, new UnbanCommand());
        commands.put(CommandEnum.CHANGE_ROLE, new ChangeRoleCommand());
        commands.put(CommandEnum.DELETE_USER, new DeleteUserCommand());
        commands.put(CommandEnum.TAKE_LOAN, new TakeLoanCommand());
        commands.put(CommandEnum.REPAY_LOAN, new RepayLoanCommand());
        commands.put(CommandEnum.FILL_RANDOM_RESULT, new FillRandomResultCommand());
        commands.put(CommandEnum.SET_COEFFICIENT, new SetCoefficientCommand());
    }

    public ICommand createCommand(CommandEnum command){
        if(commands.containsKey(command)){
            return commands.get(command);
        }
        return null;
    }
}
