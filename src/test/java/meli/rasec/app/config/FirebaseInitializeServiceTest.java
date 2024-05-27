package meli.rasec.app.config;

import com.google.auth.oauth2.GoogleCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class FirebaseInitializeServiceTest {

    FirebaseInitializeService firebaseInitialize;

    @Mock
    private GoogleCredentials googleCredentialsMock;

    @BeforeEach
    protected void setUp() throws Exception {
        openMocks(this);
        firebaseInitialize = new FirebaseInitializeService();
    }

    @Test
    void testInitialize() throws IOException {

        try (MockedStatic<GoogleCredentials> googleCredentialsMock = Mockito.mockStatic(GoogleCredentials.class)) {
            when(GoogleCredentials.getApplicationDefault()).thenReturn(null);

            assertNotNull(firebaseInitialize.toString());
        }
    }

    @Test
    void testInitializeNok() throws IOException {

        try (MockedStatic<GoogleCredentials> googleCredentials = Mockito.mockStatic(GoogleCredentials.class)) {
            when(GoogleCredentials.getApplicationDefault()).thenThrow(new IOException("error mock"));

            assertThrows(IOException.class, () -> firebaseInitialize.createFirestoreConnection());
        }
    }

}