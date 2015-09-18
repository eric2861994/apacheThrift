package if4031.client.command;

class ChangeNicknameCommand implements Command {
    private String newNickname;

    ChangeNicknameCommand(String _newNickname) {
        newNickname = _newNickname;
    }

    @Override
    public void execute() {
        // TODO impl
    }

    @Override
    public String toString() {
        return "/nick " + newNickname;
    }
}
