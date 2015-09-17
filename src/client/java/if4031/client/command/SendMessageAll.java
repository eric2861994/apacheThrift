package if4031.client.command;

class SendMessageAll implements Command {
    private final String message;

    SendMessageAll(String _message) {
        message = _message;
    }

    @Override
    public void execute() {
        // TODO impl
    }

    @Override
    public String toString() {
        return '\\' + message;
    }
}
