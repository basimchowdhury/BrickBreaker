public class CollBorderFactory {

    public static CollBorder getBorderRight(double x, double y, double w, double h){
        return new CollBorder(x,y,w,h,true,false,false,false);
    }

    public static CollBorder getBorderLeft(double x, double y, double w, double h){
        return new CollBorder(x,y,w,h,false,true,false,false);
    }

    public static CollBorder getBorderTop(double x, double y, double w, double h){
        return new CollBorder(x,y,w,h,false,false,true,false);
    }

    public static CollBorder getBorderBottom(double x, double y, double w, double h){
        return new CollBorder(x,y,w,h,false,false,false,true);
    }

    public static CollBorder getBorderPaddle(double x, double y, double w, double h) {
        return new CollBorder(x, y, w, h, true, false);
    }

    public static CollBorder getBorderBrick(double x, double y, double w, double h) {
        return new CollBorder(x,y,w,h,false, true);
    }

}
