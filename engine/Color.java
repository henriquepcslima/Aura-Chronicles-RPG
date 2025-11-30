package engine;

public class Color {
    
    public static String red(String text) {
        return "<span style='color:#ff5555; font-weight:bold'>" + text + "</span>";
    }

    
    public static String green(String text) {
        return "<span style='color:#55ff55'>" + text + "</span>";
    }

    
    public static String gold(String text) {
        return "<span style='color:#ffcc00; font-weight:bold'>" + text + "</span>";
    }

    
    public static String cyan(String text) {
        return "<span style='color:#00ffff'>" + text + "</span>";
    }

    
    public static String createBar(int current, int max, String colorHex) {
        int totalBars = 20; 
        double percent = (double) current / max;
        if (percent < 0) percent = 0;
        int filledBars = (int) (totalBars * percent);

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("<span style='color:" + colorHex + "'>");
        
        for (int i = 0; i < totalBars; i++) {
            if (i < filledBars) {
                sb.append("|"); 
            } else {
                sb.append("<span style='color:#555'>.</span>"); 
            }
        }
        
        sb.append("</span>"); 
        sb.append("]");
        return sb.toString();
    }
}