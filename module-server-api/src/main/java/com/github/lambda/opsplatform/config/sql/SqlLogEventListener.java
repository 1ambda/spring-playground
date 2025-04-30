package com.github.lambda.opsplatform.config.sql;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.spy.P6SpyOptions;
import java.sql.SQLException;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqlLogEventListener extends JdbcEventListener {

  @Override
  public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException e) {
    P6SpyOptions.getActiveInstance().setLogMessageFormat(SqlLogFormatter.class.getName());
  }
}
