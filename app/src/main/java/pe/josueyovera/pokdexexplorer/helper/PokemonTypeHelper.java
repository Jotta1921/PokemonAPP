package pe.josueyovera.pokdexexplorer.helper;

import java.util.HashMap;
import java.util.Map;

public class PokemonTypeHelper {

    public static class TypeInfo {
        public String type1;
        public String type2;

        public TypeInfo(String type1, String type2) {
            this.type1 = type1;
            this.type2 = type2;
        }
    }

    private static final Map<Integer, TypeInfo> TYPE_MAP = new HashMap<>();

    static {
        // 1-3
        TYPE_MAP.put(1, new TypeInfo("PLANTA", "VENENO"));
        TYPE_MAP.put(2, new TypeInfo("PLANTA", "VENENO"));
        TYPE_MAP.put(3, new TypeInfo("PLANTA", "VENENO"));
        // 4-6
        TYPE_MAP.put(4, new TypeInfo("FUEGO", null));
        TYPE_MAP.put(5, new TypeInfo("FUEGO", null));
        TYPE_MAP.put(6, new TypeInfo("FUEGO", "VOLADOR"));
        // 7-9
        TYPE_MAP.put(7, new TypeInfo("AGUA", null));
        TYPE_MAP.put(8, new TypeInfo("AGUA", null));
        TYPE_MAP.put(9, new TypeInfo("AGUA", null));
        // 10-12
        TYPE_MAP.put(10, new TypeInfo("BICHO", null));
        TYPE_MAP.put(11, new TypeInfo("BICHO", null));
        TYPE_MAP.put(12, new TypeInfo("BICHO", "VOLADOR"));
        // 13-15
        TYPE_MAP.put(13, new TypeInfo("BICHO", "VENENO"));
        TYPE_MAP.put(14, new TypeInfo("BICHO", "VENENO"));
        TYPE_MAP.put(15, new TypeInfo("BICHO", "VENENO"));
        // 16-18
        TYPE_MAP.put(16, new TypeInfo("NORMAL", "VOLADOR"));
        TYPE_MAP.put(17, new TypeInfo("NORMAL", "VOLADOR"));
        TYPE_MAP.put(18, new TypeInfo("NORMAL", "VOLADOR"));
        // 19-20
        TYPE_MAP.put(19, new TypeInfo("NORMAL", null));
        TYPE_MAP.put(20, new TypeInfo("NORMAL", null));
        // 21-22
        TYPE_MAP.put(21, new TypeInfo("NORMAL", "VOLADOR"));
        TYPE_MAP.put(22, new TypeInfo("NORMAL", "VOLADOR"));
        // 23-24
        TYPE_MAP.put(23, new TypeInfo("VENENO", null));
        TYPE_MAP.put(24, new TypeInfo("VENENO", null));
        // 25-26
        TYPE_MAP.put(25, new TypeInfo("ELÉCTRICO", null));
        TYPE_MAP.put(26, new TypeInfo("ELÉCTRICO", null));
        // 27-28
        TYPE_MAP.put(27, new TypeInfo("TIERRA", null));
        TYPE_MAP.put(28, new TypeInfo("TIERRA", null));
        // 29-34
        TYPE_MAP.put(29, new TypeInfo("VENENO", null));
        TYPE_MAP.put(30, new TypeInfo("VENENO", null));
        TYPE_MAP.put(31, new TypeInfo("VENENO", "TIERRA"));
        TYPE_MAP.put(32, new TypeInfo("VENENO", null));
        TYPE_MAP.put(33, new TypeInfo("VENENO", null));
        TYPE_MAP.put(34, new TypeInfo("VENENO", "TIERRA"));
        // 35-36
        TYPE_MAP.put(35, new TypeInfo("HADA", null));
        TYPE_MAP.put(36, new TypeInfo("HADA", null));
        // 37-38
        TYPE_MAP.put(37, new TypeInfo("FUEGO", null));
        TYPE_MAP.put(38, new TypeInfo("FUEGO", null));
        // 39-40
        TYPE_MAP.put(39, new TypeInfo("NORMAL", "HADA"));
        TYPE_MAP.put(40, new TypeInfo("NORMAL", "HADA"));
        // 41-42
        TYPE_MAP.put(41, new TypeInfo("VENENO", "VOLADOR"));
        TYPE_MAP.put(42, new TypeInfo("VENENO", "VOLADOR"));
        // 43-45
        TYPE_MAP.put(43, new TypeInfo("PLANTA", "VENENO"));
        TYPE_MAP.put(44, new TypeInfo("PLANTA", "VENENO"));
        TYPE_MAP.put(45, new TypeInfo("PLANTA", "VENENO"));
        // 46-47
        TYPE_MAP.put(46, new TypeInfo("BICHO", "PLANTA"));
        TYPE_MAP.put(47, new TypeInfo("BICHO", "PLANTA"));
        // 48-49
        TYPE_MAP.put(48, new TypeInfo("BICHO", "VENENO"));
        TYPE_MAP.put(49, new TypeInfo("BICHO", "VENENO"));
        // 50-51
        TYPE_MAP.put(50, new TypeInfo("TIERRA", null));
        TYPE_MAP.put(51, new TypeInfo("TIERRA", null));
        // 52-53
        TYPE_MAP.put(52, new TypeInfo("NORMAL", null));
        TYPE_MAP.put(53, new TypeInfo("NORMAL", null));
        // 54-55
        TYPE_MAP.put(54, new TypeInfo("AGUA", null));
        TYPE_MAP.put(55, new TypeInfo("AGUA", null));
        // 56-57
        TYPE_MAP.put(56, new TypeInfo("LUCHA", null));
        TYPE_MAP.put(57, new TypeInfo("LUCHA", null));
        // 58-59
        TYPE_MAP.put(58, new TypeInfo("FUEGO", null));
        TYPE_MAP.put(59, new TypeInfo("FUEGO", null));
        // 60-62
        TYPE_MAP.put(60, new TypeInfo("AGUA", null));
        TYPE_MAP.put(61, new TypeInfo("AGUA", null));
        TYPE_MAP.put(62, new TypeInfo("AGUA", "LUCHA"));
        // 63-65
        TYPE_MAP.put(63, new TypeInfo("PSÍQUICO", null));
        TYPE_MAP.put(64, new TypeInfo("PSÍQUICO", null));
        TYPE_MAP.put(65, new TypeInfo("PSÍQUICO", null));
        // 66-68
        TYPE_MAP.put(66, new TypeInfo("LUCHA", null));
        TYPE_MAP.put(67, new TypeInfo("LUCHA", null));
        TYPE_MAP.put(68, new TypeInfo("LUCHA", null));
        // 69-71
        TYPE_MAP.put(69, new TypeInfo("PLANTA", "VENENO"));
        TYPE_MAP.put(70, new TypeInfo("PLANTA", "VENENO"));
        TYPE_MAP.put(71, new TypeInfo("PLANTA", "VENENO"));
        // 72-73
        TYPE_MAP.put(72, new TypeInfo("AGUA", "VENENO"));
        TYPE_MAP.put(73, new TypeInfo("AGUA", "VENENO"));
        // 74-76
        TYPE_MAP.put(74, new TypeInfo("ROCA", "TIERRA"));
        TYPE_MAP.put(75, new TypeInfo("ROCA", "TIERRA"));
        TYPE_MAP.put(76, new TypeInfo("ROCA", "TIERRA"));
        // 77-78
        TYPE_MAP.put(77, new TypeInfo("FUEGO", null));
        TYPE_MAP.put(78, new TypeInfo("FUEGO", null));
        // 79-80
        TYPE_MAP.put(79, new TypeInfo("AGUA", "PSÍQUICO"));
        TYPE_MAP.put(80, new TypeInfo("AGUA", "PSÍQUICO"));
        // 81-82
        TYPE_MAP.put(81, new TypeInfo("ELÉCTRICO", "ACERO"));
        TYPE_MAP.put(82, new TypeInfo("ELÉCTRICO", "ACERO"));
        // 83
        TYPE_MAP.put(83, new TypeInfo("NORMAL", "VOLADOR"));
        // 84-85
        TYPE_MAP.put(84, new TypeInfo("NORMAL", "VOLADOR"));
        TYPE_MAP.put(85, new TypeInfo("NORMAL", "VOLADOR"));
        // 86-87
        TYPE_MAP.put(86, new TypeInfo("AGUA", null));
        TYPE_MAP.put(87, new TypeInfo("AGUA", "HIELO"));
        // 88-89
        TYPE_MAP.put(88, new TypeInfo("VENENO", null));
        TYPE_MAP.put(89, new TypeInfo("VENENO", null));
        // 90-91
        TYPE_MAP.put(90, new TypeInfo("AGUA", null));
        TYPE_MAP.put(91, new TypeInfo("AGUA", "HIELO"));
        // 92-94
        TYPE_MAP.put(92, new TypeInfo("FANTASMA", "VENENO"));
        TYPE_MAP.put(93, new TypeInfo("FANTASMA", "VENENO"));
        TYPE_MAP.put(94, new TypeInfo("FANTASMA", "VENENO"));
        // 95
        TYPE_MAP.put(95, new TypeInfo("ROCA", "TIERRA"));
        // 96-97
        TYPE_MAP.put(96, new TypeInfo("PSÍQUICO", null));
        TYPE_MAP.put(97, new TypeInfo("PSÍQUICO", null));
        // 98-99
        TYPE_MAP.put(98, new TypeInfo("AGUA", null));
        TYPE_MAP.put(99, new TypeInfo("AGUA", null));
        // 100-101
        TYPE_MAP.put(100, new TypeInfo("ELÉCTRICO", null));
        TYPE_MAP.put(101, new TypeInfo("ELÉCTRICO", null));
        // 102-103
        TYPE_MAP.put(102, new TypeInfo("PLANTA", "PSÍQUICO"));
        TYPE_MAP.put(103, new TypeInfo("PLANTA", "PSÍQUICO"));
        // 104-105
        TYPE_MAP.put(104, new TypeInfo("TIERRA", null));
        TYPE_MAP.put(105, new TypeInfo("TIERRA", null));
        // 106-107
        TYPE_MAP.put(106, new TypeInfo("LUCHA", null));
        TYPE_MAP.put(107, new TypeInfo("LUCHA", null));
        // 108
        TYPE_MAP.put(108, new TypeInfo("NORMAL", null));
        // 109-110
        TYPE_MAP.put(109, new TypeInfo("VENENO", null));
        TYPE_MAP.put(110, new TypeInfo("VENENO", null));
        // 111-112
        TYPE_MAP.put(111, new TypeInfo("ROCA", "TIERRA"));
        TYPE_MAP.put(112, new TypeInfo("ROCA", "TIERRA"));
        // 113
        TYPE_MAP.put(113, new TypeInfo("NORMAL", null));
        // 114
        TYPE_MAP.put(114, new TypeInfo("PLANTA", null));
        // 115
        TYPE_MAP.put(115, new TypeInfo("NORMAL", null));
        // 116-117
        TYPE_MAP.put(116, new TypeInfo("AGUA", null));
        TYPE_MAP.put(117, new TypeInfo("AGUA", null));
        // 118-119
        TYPE_MAP.put(118, new TypeInfo("AGUA", null));
        TYPE_MAP.put(119, new TypeInfo("AGUA", null));
        // 120-121
        TYPE_MAP.put(120, new TypeInfo("AGUA", null));
        TYPE_MAP.put(121, new TypeInfo("AGUA", "PSÍQUICO"));
        // 122
        TYPE_MAP.put(122, new TypeInfo("PSÍQUICO", "HADA"));
        // 123
        TYPE_MAP.put(123, new TypeInfo("BICHO", "VOLADOR"));
        // 124
        TYPE_MAP.put(124, new TypeInfo("HIELO", "PSÍQUICO"));
        // 125
        TYPE_MAP.put(125, new TypeInfo("ELÉCTRICO", null));
        // 126
        TYPE_MAP.put(126, new TypeInfo("FUEGO", null));
        // 127
        TYPE_MAP.put(127, new TypeInfo("BICHO", null));
        // 128
        TYPE_MAP.put(128, new TypeInfo("NORMAL", null));
        // 129-130
        TYPE_MAP.put(129, new TypeInfo("AGUA", null));
        TYPE_MAP.put(130, new TypeInfo("AGUA", "VOLADOR"));
        // 131
        TYPE_MAP.put(131, new TypeInfo("AGUA", "HIELO"));
        // 132
        TYPE_MAP.put(132, new TypeInfo("NORMAL", null));
        // 133
        TYPE_MAP.put(133, new TypeInfo("NORMAL", null));
        // 134
        TYPE_MAP.put(134, new TypeInfo("AGUA", null));
        // 135
        TYPE_MAP.put(135, new TypeInfo("ELÉCTRICO", null));
        // 136
        TYPE_MAP.put(136, new TypeInfo("FUEGO", null));
        // 137
        TYPE_MAP.put(137, new TypeInfo("NORMAL", null));
        // 138-139
        TYPE_MAP.put(138, new TypeInfo("ROCA", "AGUA"));
        TYPE_MAP.put(139, new TypeInfo("ROCA", "AGUA"));
        // 140-141
        TYPE_MAP.put(140, new TypeInfo("ROCA", "AGUA"));
        TYPE_MAP.put(141, new TypeInfo("ROCA", "AGUA"));
        // 142
        TYPE_MAP.put(142, new TypeInfo("ROCA", "VOLADOR"));
        // 143
        TYPE_MAP.put(143, new TypeInfo("NORMAL", null));
        // 144
        TYPE_MAP.put(144, new TypeInfo("HIELO", "VOLADOR"));
        // 145
        TYPE_MAP.put(145, new TypeInfo("ELÉCTRICO", "VOLADOR"));
        // 146
        TYPE_MAP.put(146, new TypeInfo("FUEGO", "VOLADOR"));
        // 147-149
        TYPE_MAP.put(147, new TypeInfo("DRAGÓN", null));
        TYPE_MAP.put(148, new TypeInfo("DRAGÓN", null));
        TYPE_MAP.put(149, new TypeInfo("DRAGÓN", "VOLADOR"));
        // 150-151
        TYPE_MAP.put(150, new TypeInfo("PSÍQUICO", null));
        TYPE_MAP.put(151, new TypeInfo("PSÍQUICO", null));
    }

    public static TypeInfo getTypeInfo(int id) {
        if (TYPE_MAP.containsKey(id)) {
            return TYPE_MAP.get(id);
        }
        return new TypeInfo("NORMAL", null);
    }

    public static boolean hasType(int id, String typeName) {
        if (typeName == null || typeName.isEmpty()) return true;
        TypeInfo info = getTypeInfo(id);
        String search = typeName.toUpperCase().trim();
        if (info.type1 != null && info.type1.equalsIgnoreCase(search)) return true;
        if (info.type2 != null && info.type2.equalsIgnoreCase(search)) return true;
        return false;
    }

    public static String getTypeColor(String type) {
        if (type == null) return "#475569";
        switch (type.toUpperCase()) {
            case "FUEGO": return "#DC2626";
            case "AGUA": return "#2563EB";
            case "PLANTA": return "#16A34A";
            case "ELÉCTRICO": return "#EAB308";
            case "VENENO": return "#9333EA";
            case "BICHO": return "#65A30D";
            case "VOLADOR": return "#0284C7";
            case "TIERRA": return "#D97706";
            case "ROCA": return "#B45309";
            case "PSÍQUICO": return "#E11D48";
            case "FANTASMA": return "#6366F1";
            case "DRAGÓN": return "#4F46E5";
            case "HIELO": return "#06B6D4";
            case "LUCHA": return "#C2410C";
            case "HADA": return "#EC4899";
            case "ACERO": return "#64748B";
            case "NORMAL":
            default: return "#475569";
        }
    }
}
