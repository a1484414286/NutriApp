package controller;
import java.util.Stack;

import com.model.User.History;
import com.model.User.User;

public class BrowseHistory implements Command<Stack<History>> {
    private User user;

    public BrowseHistory(User user) {
        this.user = user;
    }

    @Override
    public Stack<History> execute() {
        return user.getAllHistory();
    }
}
