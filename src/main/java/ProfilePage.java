// Open your own profile for editing
// Create Java Class with String variables which will
// contain css and xpath locators for each field, including  buttons.

public class ProfilePage {

    private String nameCss = "[name = 'name'], [class*='editUserProfile_fieldBox'] [name='name']";
    private String nameXpath = "//*[@name = 'name'], //div[starts-with(@class,'editUserProfile_fieldBox')]//*[@name='name']";
    private String nicknameCss = "[name = 'login'], [class*='editUserProfile_fieldBox'] [name='login']";
    private String nicknameXpath ="//input[@name = 'login'], //div[starts-with(@class,'editUserProfile_fieldBox')]//*[@name='login']";
    private String phoneNumberCss = "[name = 'phoneNumber'], [class*='editUserProfile_fieldBox'] [name='phoneNumber']";
    private String phoneNumberXpath = "//*[@name = 'phoneNumber'], //div[starts-with(@class,'editUserProfile_fieldBox')]//*[@name='phoneNumber']";
    private String positionCss = "[name = 'position'], [class*='editUserProfile_fieldBox'] [name='position']";
    private String positionXpath = "//*[@name = 'position'], //div[starts-with(@class,'editUserProfile_fieldBox')]//*[@name='position']";
    private String departmentCss = "[name = 'department'], [class*='editUserProfile_fieldBox'] [name='department']";
    private String departmentXpath = "//*[@name = 'department'], //div[starts-with(@class,'editUserProfile_fieldBox')]//*[@name='department']";
    private String cancelButtonCss = "[class*='editUserProfile_buttonsBox'] [class*='editUserProfile_cancelBtn']";
    private String cancelButtonXpath = "//button[@type='button' and contains(., 'Cancel')], //button[text()='Cancel']";
    private String SubmitButtonCss = "[type='submit'], [type='submit'][class*='button_btn']";
    private String SubmitButtonXpath = "//button[@type='submit' and contains(.,'Save Changes')], //button[text()='Save Changes']";

}
