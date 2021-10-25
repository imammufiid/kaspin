package com.example.kasirpintartest.ui.transaction

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.kasirpintartest.data.RepositoryImpl
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.utils.DataDummy
import com.example.kasirpintartest.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TransactionViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: TransactionViewModel

    @Mock
    private lateinit var repositoryImpl: RepositoryImpl

    @Mock
    private lateinit var observerMessage: Observer<String>

    @Mock
    private lateinit var observerProduct: Observer<List<Product>>

    @Test
    fun `get product success`() {
        val dataDummy = DataDummy.generateProducts()
        val response = MutableLiveData<List<Product>>()
        response.value = dataDummy

        testCoroutineRule.runBlockingTest {
            doReturn(response)
                .`when`(repositoryImpl)
                .getProducts()

            viewModel = TransactionViewModel(repositoryImpl)
            viewModel.getProducts()

            val products = viewModel.products.value
            verify(repositoryImpl, times(1)).getProducts()

            assertEquals(dataDummy.size, products?.size)
            assertNotNull(products)

            viewModel.products.observeForever(observerProduct)
            verify(observerProduct).onChanged(dataDummy)
            viewModel.products.removeObserver(observerProduct)
        }
    }
}