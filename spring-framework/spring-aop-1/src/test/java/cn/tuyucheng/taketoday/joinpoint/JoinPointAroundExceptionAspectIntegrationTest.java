package cn.tuyucheng.taketoday.joinpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableAspectJAutoProxy
class JoinPointAroundExceptionAspectIntegrationTest {

    private final List<String> messages = new ArrayList<>();
    @Autowired
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        Handler logEventHandler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                messages.add(record.getLevel().getName() + " " + record.getMessage());
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
        };

        Logger logger = Logger.getLogger(JoinPointAroundExceptionAspect.class.getName());
        logger.addHandler(logEventHandler);
    }

    @Test/*(expected = IllegalArgumentException.class)*/
    void shouldLogMethodSignatureBeforeExecution() {
        articleService.getArticleList(" ");

        assertThat(messages, hasSize(1));
        assertTrue(messages.contains("INFO Retrying operation"));
    }
}