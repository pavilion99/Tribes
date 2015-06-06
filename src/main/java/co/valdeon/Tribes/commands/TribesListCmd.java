package co.valdeon.Tribes.commands;

import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.command.CommandSender;

class TribesListCmd {

    public void execute(CommandSender sender, String[] args) {
        int page;
        if(args.length == 1)
            page = 1;
        else {
            if(args[1].equals("0")) {
                Message.message(sender, TribesCmd.err(), Config.nonZero);
                return;
            }
            page = Integer.parseInt(args[1]);
        }

        int totalPages = (int)Math.ceil(TribeLoader.tribesList.size()/6.0d);

        if(page > totalPages) {
            Message.message(sender, TribesCmd.err(), Config.tooManyPages);
            return;
        }

        Message.message(sender, Message.format(Config.listTribes, Config.colorOne, Config.colorTwo, Integer.toString(page), Integer.toString(totalPages)));

        for(int i = (6 * (page - 1)); i < (6 * page); i++) {
            if(i >= TribeLoader.tribesList.size())
                break;
            Message.message(sender, Config.colorOne, TribeLoader.tribesList.get(i).getName(), Config.colorTwo);
        }
    }

}
