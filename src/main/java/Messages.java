public enum Messages {
    BUSY("Подождите пожалуйста, я занят другим пользователем."),
    WrongNumFormat("Ведите числовое значение!"),
    CHOOSE("Пожалуйста выберите валюту!"),
    MoneyValue("Введите сколько у вас рублей");



    private String title;
    Messages(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
