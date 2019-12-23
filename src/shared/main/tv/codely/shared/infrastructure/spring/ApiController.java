package tv.codely.shared.infrastructure.spring;

import org.springframework.http.HttpStatus;
import tv.codely.shared.domain.DomainError;
import tv.codely.shared.domain.bus.command.Command;
import tv.codely.shared.domain.bus.command.CommandBus;
import tv.codely.shared.domain.bus.command.CommandHandlerExecutionError;
import tv.codely.shared.domain.bus.command.CommandNotRegisteredError;
import tv.codely.shared.domain.bus.query.Query;
import tv.codely.shared.domain.bus.query.QueryBus;
import tv.codely.shared.domain.bus.query.QueryHandlerExecutionError;
import tv.codely.shared.domain.bus.query.QueryNotRegisteredError;

import java.util.HashMap;

public abstract class ApiController {
    private final QueryBus   queryBus;
    private final CommandBus commandBus;

    public ApiController(QueryBus queryBus, CommandBus commandBus) {
        this.queryBus   = queryBus;
        this.commandBus = commandBus;
    }

    protected void dispatch(Command command) throws CommandNotRegisteredError, CommandHandlerExecutionError {
        commandBus.dispatch(command);
    }

    protected <R> R ask(Query query) throws QueryNotRegisteredError, QueryHandlerExecutionError {
        return queryBus.ask(query);
    }

    abstract protected HashMap<Class<? extends DomainError>, HttpStatus> errorMapping();
}