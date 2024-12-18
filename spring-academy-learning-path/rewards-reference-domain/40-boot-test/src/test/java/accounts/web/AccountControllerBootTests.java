package accounts.web;

import accounts.AccountManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import rewards.internal.account.Account;

import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Replace @ExtendWith(SpringExtension.class) with the following annotation
@WebMvcTest(AccountController.class) // includes @ExtendWith(SpringExtension.class)
public class AccountControllerBootTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountManager accountManager;

    // Write positive test for GET request for an account
    // - Uncomment the code and run the test and verify it succeeds
    @Test
    public void accountDetails() throws Exception {
        given(accountManager.getAccount(0L))
                .willReturn(new Account("1234567890", "John Doe"));

        mockMvc.perform(get("/accounts/0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("name").value("John Doe"))
                .andExpect(jsonPath("number").value("1234567890"));

        verify(accountManager).getAccount(0L);
    }

    // Write negative test for GET request for a non-existent account
    // - Uncomment the "given" and "verify" statements
    // - Write code between the "given" and "verify" statements
    // - Run the test and verify it succeeds
    @Test
    public void accountDetailsFail() throws Exception {

        given(accountManager.getAccount(any(Long.class)))
                .willThrow(new IllegalArgumentException("No such account with id " + 0L));

        // (Write code here)
        // - Use mockMvc to perform HTTP Get operation using "/accounts/9999"
        //   as a non-existent account URL
        // - Verify that the HTTP response status is 404
        mockMvc.perform(get("/accounts/9999"))
                .andExpect(status().isNotFound());
        verify(accountManager).getAccount(any(Long.class));
    }

    // Write test for `POST` request for an account
    // - Uncomment Java code below
    // - Write code between the "given" and "verify" statements
    // - Run the test and verify it succeeds
    @Test
    public void createAccount() throws Exception {
        Account testAccount = new Account("1234512345", "Mary Jones");
        testAccount.setEntityId(21L);

        given(accountManager.save(any(Account.class)))
                .willReturn(testAccount);

        mockMvc.perform(post("/accounts")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(testAccount)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/accounts/21"));

        verify(accountManager).save(any(Account.class));
    }

    // Utility class for converting an object into JSON string
    protected static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Experiment with @MockBean vs @Mock
    // - Change `@MockBean` to `@Mock` for the `AccountManager dependency above
    // - Run the test and observe a test failure
    // - Change it back to `@MockBean`

}
