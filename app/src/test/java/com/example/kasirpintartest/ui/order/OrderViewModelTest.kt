package com.example.kasirpintartest.ui.order

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.kasirpintartest.data.RepositoryImpl
import com.example.kasirpintartest.data.entity.Order
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.ui.transaction.TransactionViewModel
import com.example.kasirpintartest.utils.DataDummy
import com.example.kasirpintartest.utils.TestCoroutineRule
import com.example.kasirpintartest.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.bytebuddy.implementation.MethodCall.invoke
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class OrderViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: OrderViewModel

    @Mock
    private lateinit var repositoryImpl: RepositoryImpl

    @Mock
    private lateinit var observerOrder: Observer<List<Order>>

    @Test
    fun getOrder() {
        val dataDummy = DataDummy.generateOrders()
        val response = MutableLiveData<List<Order>>()
        response.value = dataDummy

        testCoroutineRule.runBlockingTest {
            doAnswer { invocation ->
                (invocation.arguments[0] as (Resource<List<Order>>) -> Unit).invoke(
                    Resource.success(
                        dataDummy
                    )
                )
            }.`when`(repositoryImpl).getOrders(any())

            viewModel = OrderViewModel(repositoryImpl)
            viewModel.getOrder()

            val products = viewModel.order.value
            verify(repositoryImpl, times(1)).getOrders(any())

            assertEquals(dataDummy.size, products?.size)
            assertNotNull(products)

            viewModel.order.observeForever(observerOrder)
            verify(observerOrder).onChanged(dataDummy)
            viewModel.order.removeObserver(observerOrder)
        }
    }
}