package org.molgenis.data.security.auth;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.molgenis.data.meta.AttributeType.BOOL;
import static org.molgenis.data.meta.AttributeType.ENUM;
import static org.molgenis.data.meta.AttributeType.TEXT;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_ID;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_LABEL;
import static org.molgenis.data.meta.model.EntityType.AttributeRole.ROLE_LOOKUP;
import static org.molgenis.data.meta.model.Package.PACKAGE_SEPARATOR;
import static org.molgenis.data.security.auth.SecurityPackage.PACKAGE_SECURITY;
import static org.molgenis.util.i18n.LanguageService.getLanguageCodes;

import org.molgenis.data.meta.AttributeType;
import org.molgenis.data.meta.SystemEntityType;
import org.springframework.stereotype.Component;

@Component
public class UserMetadata extends SystemEntityType {
  private static final String SIMPLE_NAME = "User";
  public static final String USER = PACKAGE_SECURITY + PACKAGE_SEPARATOR + SIMPLE_NAME;

  public static final String USERNAME = "username";
  public static final String SUPERUSER = "superuser";

  @SuppressWarnings("squid:S2068") // this is not a hardcoded password
  public static final String PASSWORD = "password_";

  public static final String TWO_FACTOR_AUTHENTICATION = "use2fa";
  public static final String EMAIL = "Email";
  public static final String GOOGLEACCOUNTID = "googleAccountId";
  public static final String ACTIVATIONCODE = "activationCode";
  public static final String ACTIVE = "active";

  @SuppressWarnings("squid:S2068") // this is not a hardcoded password
  public static final String CHANGE_PASSWORD = "changePassword";

  public static final String FIRSTNAME = "FirstName";
  public static final String MIDDLENAMES = "MiddleNames";
  public static final String LASTNAME = "LastName";
  public static final String TITLE = "Title";
  public static final String AFFILIATION = "Affiliation";
  public static final String DEPARTMENT = "Department";
  public static final String ROLE = "Role";
  public static final String ADDRESS = "Address";
  public static final String PHONE = "Phone";
  public static final String TOLLFREEPHONE = "tollFreePhone";
  public static final String FAX = "Fax";
  public static final String CITY = "City";
  public static final String COUNTRY = "Country";
  public static final String LANGUAGECODE = "languageCode";
  public static final String ID = "id";

  private final SecurityPackage securityPackage;

  public UserMetadata(SecurityPackage securityPackage) {
    super(SIMPLE_NAME, PACKAGE_SECURITY);
    this.securityPackage = requireNonNull(securityPackage);
  }

  @Override
  public void init() {
    setLabel("User");
    setPackage(securityPackage);

    setDescription("Anyone who can login");

    addAttribute(ID, ROLE_ID)
        .setAuto(true)
        .setVisible(false)
        .setDescription("automatically generated internal id, only for internal use.");
    addAttribute(USERNAME, ROLE_LABEL, ROLE_LOOKUP)
        .setLabel("Username")
        .setUnique(true)
        .setNillable(false)
        .setReadOnly(true)
        .setValidationExpression("$('" + USERNAME + "').matches(/^[\\S].+[\\S]$/).value()");
    addAttribute(PASSWORD)
        .setLabel("Password")
        .setDescription("This is the hashed password, enter a new plaintext password to update.")
        .setNillable(false);
    addAttribute(ACTIVATIONCODE)
        .setLabel("Activation code")
        .setNillable(true)
        .setDescription(
            "Used as alternative authentication mechanism to verify user email and/or if user has lost password.");
    addAttribute(ACTIVE)
        .setLabel("Active")
        .setDataType(BOOL)
        .setDefaultValue(FALSE.toString())
        .setDescription("Boolean to indicate if this account can be used to login")
        .setAggregatable(true)
        .setNillable(false);
    addAttribute(SUPERUSER)
        .setLabel("Superuser")
        .setDataType(BOOL)
        .setDefaultValue(FALSE.toString())
        .setAggregatable(true)
        .setNillable(false);
    addAttribute(FIRSTNAME).setLabel("First name").setNillable(true);
    addAttribute(MIDDLENAMES).setLabel("Middle names").setNillable(true);
    addAttribute(LASTNAME, ROLE_LOOKUP).setLabel("Last name").setNillable(true);
    addAttribute(TITLE)
        .setLabel(TITLE)
        .setNillable(true)
        .setDescription("An academic title, e.g. Prof.dr, PhD");
    addAttribute(AFFILIATION).setLabel(AFFILIATION).setNillable(true);
    addAttribute(DEPARTMENT)
        .setDescription(
            "Added from the old definition of MolgenisUser. Department of this contact.")
        .setNillable(true);
    addAttribute(ROLE)
        .setDescription("Indicate role of the contact, e.g. lab worker or PI.")
        .setNillable(true);
    addAttribute(ADDRESS)
        .setDescription("The address of the Contact.")
        .setNillable(true)
        .setDataType(TEXT);
    addAttribute(PHONE)
        .setDescription("The telephone number of the Contact including the suitable area codes.")
        .setNillable(true);
    addAttribute(EMAIL, ROLE_LOOKUP)
        .setDescription("The email address of the Contact.")
        .setUnique(true)
        .setDataType(AttributeType.EMAIL)
        .setNillable(false);
    addAttribute(FAX).setDescription("The fax number of the Contact.").setNillable(true);
    addAttribute(TOLLFREEPHONE)
        .setLabel("Toll-free phone")
        .setNillable(true)
        .setDescription("A toll free phone number for the Contact, including suitable area codes.");
    addAttribute(CITY)
        .setDescription("Added from the old definition of MolgenisUser. City of this contact.")
        .setNillable(true);
    addAttribute(COUNTRY)
        .setDescription("Added from the old definition of MolgenisUser. Country of this contact.")
        .setNillable(true);
    addAttribute(CHANGE_PASSWORD)
        .setLabel("Change password")
        .setDataType(BOOL)
        .setDefaultValue(FALSE.toString())
        .setDescription("If true the user must first change his password before he can proceed")
        .setAggregatable(true)
        .setNillable(false);
    addAttribute(TWO_FACTOR_AUTHENTICATION)
        .setLabel("Use two factor authentication")
        .setDataType(BOOL)
        .setDefaultValue(FALSE.toString())
        .setDescription(
            "Enables two factor authentication for this user if the application supports it");
    addAttribute(LANGUAGECODE)
        .setLabel("Language code")
        .setDescription("Selected language for this site.")
        .setDataType(ENUM)
        .setEnumOptions(getLanguageCodes().collect(toList()))
        .setNillable(true);
    addAttribute(GOOGLEACCOUNTID)
        .setLabel("Google account ID")
        .setDescription(
            "An identifier for the user, unique among all Google accounts and never reused.")
        .setNillable(true);
  }
}
