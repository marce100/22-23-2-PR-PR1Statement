package uoc.ds.pr.util;

public class ResourceUtil {



    public static byte getFlag(byte flagTouchScreen, byte flagComputer) {
        return 0;
    }

    public static byte getFlag(byte flagComputer, byte flagAuxiliaryMic, byte flagTouchScreen) {
        return 0;
    }

    public static byte getFlag(byte flagAuxiliaryMic, byte flagVideoProjector, byte flagComputer, byte flagTouchScreen) {
        return 0;
    }

    public static byte getFlag(byte flagAllOpts) {
        return 0;
    }

    public static boolean hasComputer(byte resource) {
        return false;
    }

    public static boolean hasAuxiliaryMic(byte resource) {
        return false;
    }

    public static boolean hasTouchScreen(byte resource) {
        return false;
    }

    public static boolean hasVideoProjector(byte resource) {
        return false;
    }

    public static boolean hasAllOpts(byte resource) {
        return false;
    }
}
