package com.forever.test.junit;

import com.forever.test.junit.demo.DaoDemo;
import com.forever.test.junit.demo.AddServiceDemo;
import com.forever.test.junit.demo.ServiceDemo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

/**
 * @author WJX
 * @date 2021/4/25 11:23
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({DataSourceTransactionManager.class, SpringUtils.class})
public class PowerMockDemo {

    @Mock
    private DaoDemo dao;

    @InjectMocks
    private ServiceDemo service;

    @InjectMocks
    private AddServiceDemo addService;

    @Spy
    private DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();

    @Before
    public void before() {
        // mock注解初始化
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void serviceTest() {
        Mockito.when(dao.select()).thenReturn(1);
        Assert.assertEquals(1, service.doSelect());
    }


    @Test
    public void addServiceTest() {
        // addService 注入 service
        ReflectionTestUtils.setField(addService, "service", service);

        Mockito.when(dao.select()).thenReturn(1);
        Assert.assertEquals(2, addService.add());
    }


    @Test
    @SuppressWarnings("all")
    public void springUtilsTest() {

        // 静态工具初始化
        PowerMockito.mockStatic(SpringUtils.class);
        // 获取失败
        Assert.assertNull(SpringUtils.getBean(DataSource.class));

        // 数据源
        DataSource source = Mockito.mock(DataSource.class, Mockito.RETURNS_MOCKS);
        PowerMockito.when(SpringUtils.getBean(DataSource.class)).thenReturn(source);

        // 获取成功
        Assert.assertEquals(source, SpringUtils.getBean(DataSource.class));
    }


    @Test
    public void transactionManagerTest() {

        // 事务管理器
        transactionManager.setDataSource(Mockito.mock(DataSource.class, Mockito.RETURNS_MOCKS));

        // 事务状态
        TransactionStatus status = PowerMockito.mock(TransactionStatus.class);
        PowerMockito.doReturn(status).when(transactionManager).getTransaction(Mockito.any());

        // 提交或回滚
        Mockito.doNothing().when(transactionManager).commit(Mockito.any(TransactionStatus.class));
        Mockito.doNothing().when(transactionManager).rollback(Mockito.any(TransactionStatus.class));

        transactionManager.commit(transactionManager.getTransaction(new DefaultTransactionDefinition()));
        transactionManager.rollback(transactionManager.getTransaction(new DefaultTransactionDefinition()));
    }


}
