package utils;

public enum Section {
    ТЕЛЕФОНЫ("Телефоны"),
    ПЛАНШЕТЫ("Планшеты"),
    ТЕЛЕВИЗОРЫ("Телевизоры"),
    ФОТО_ВИДЕО("Фото/видео"),
    ЧАСЫ("Часы");

    private final String displayName;

    Section(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static String convert(String sectionKey) {
        try {
            return valueOf(sectionKey.replace("/", "_")).getDisplayName();
        } catch (IllegalArgumentException e) {
            return sectionKey;
        }
    }
}