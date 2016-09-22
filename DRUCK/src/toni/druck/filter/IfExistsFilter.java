package toni.druck.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Thomas Nill
 * 
 * Sendet die DataItem zwischen zwei 
 * DataItems aus exists
 * 
 * wenn exclude = false ist und ein DataItem command 
 * zwischen zwei Elementen aus exists existiert
 * 
 * wenn exclude = true ist und kein DataItem command
 * zwischen zwei Elementen aus exists existiert
 * 
 * @see BFilter 
 *  
 */
import toni.druck.page.DataItem;

/**
 * 
 * @author Thomas Nill
 * 
 *         Dieser Filter teilt den Datenstrom in Abschnitte, die durch die
 *         {@link DataItem} mit getCommand = command begrent werden d.h
 * 
 *         ...Abschnitt... command ...Abschnitt... command
 * 
 *         Dieser Abschnitte werden aus dem Datenstrom entfernt bzw. erhalten
 * 
 *         ist exclude = false
 * 
 *         wird der Abschnitt erhalten wenn in ihm DataItem mit getCommand in
 *         exists vorkommen, ansonsten wird dieser Abschnit eliminiert
 * 
 *         ist exclude = true
 * 
 *         wird der Abschnitt erhalten wenn in ihm kein DataItem mit getCommand
 *         in exists vorkommt, ansonsten wird dieser Abschnit eliminiert
 * 
 * 
 * 
 */
public class IfExistsFilter extends BasisFilter {
    private boolean exclude = false;

    private String command;
    private String[] exists;

    private List<DataItem> betweenItems = new ArrayList<DataItem>(); // DataItems
                                                                    // zwischen
                                                                    // zwei aus
                                                                    // exits
    private List<DataItem> preItems = new ArrayList<DataItem>();

    enum Status {
        PRE_COMMAND, COMMAND, FOUND
    };

    private Status status = Status.PRE_COMMAND;

    public IfExistsFilter() {
        super();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    public void setExists(String append) {
        this.exists = append.split(" *, *");
    }

    @Override
    public void receive(DataItem item) {
        String itemCommand = item.getCommand();
        boolean commandFound = (getCommand().equals(itemCommand))
                || itemCommand.equals(DataItem.ENDOFFILE);
        if (exclude) {
            status = nextStatusExclude(status, commandFound, item);
        } else {
            status = nextStatusInclude(status, commandFound, item);
        }
        if (itemCommand.equals(DataItem.ENDOFFILE)) {
            preItems.clear();
            betweenItems.clear();
            send(item);
        }
    }

    private Status nextStatusInclude(Status status, boolean commandFound,
            DataItem item) {
        switch (status) {
        case PRE_COMMAND:
            if (commandFound) {
                betweenItems.add(item);
                return Status.COMMAND;
            } else {
                preItems.add(item);
            }
            break;
        case COMMAND:
            if (commandFound) {
                betweenItems.clear();
            }
            betweenItems.add(item);
            if (!commandFound) {
                boolean existsFound = getFound(item.getCommand());
                if (existsFound) {
                    return Status.FOUND;
                }
            }
            break;
        case FOUND:
            if (commandFound) {
                send(preItems);
                send(betweenItems);
                betweenItems.add(item);
                return Status.COMMAND;
            } else {
                betweenItems.add(item);
            }
            break;
        default:
            throw new RuntimeException("nicht erlaubter Status");

        }

        return status;
    }

    private Status nextStatusExclude(Status status, boolean commandFound,
            DataItem item) {
        switch (status) {
        case PRE_COMMAND:
            if (commandFound) {
                betweenItems.add(item);
                return Status.FOUND;
            } else {
                preItems.add(item);
            }
            break;
        case FOUND:
            if (commandFound) {
                send(preItems);
                send(betweenItems);
            }
            betweenItems.add(item);
            if (!commandFound) {
                boolean existsFound = getFound(item.getCommand());
                if (existsFound) {
                    return Status.COMMAND;
                }
            }
            break;
        case COMMAND:
            if (commandFound) {
                betweenItems.clear();
                betweenItems.add(item);
                return Status.FOUND;
            }
            break;
        default:
            throw new RuntimeException("nicht erlaubter Status");

        }

        return status;
    }

    private void send(List<DataItem> items) {
       for(DataItem item : items) {
           send(item);
       }
        
    }

    private boolean getFound(String command) {
        for (String e : exists) {
            if (command.equals(e)) {
                return true;
            }
        }
        return false;
    }

  

}
