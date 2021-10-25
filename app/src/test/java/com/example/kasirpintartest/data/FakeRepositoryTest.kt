package com.example.kasirpintartest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.data.remote.RemoteDataSource
import com.example.kasirpintartest.ui.transaction.TransactionViewModel
import com.example.kasirpintartest.utils.DataDummy
import com.example.kasirpintartest.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FakeRepositoryTest {
    private lateinit var fakeRepository: FakeRepository

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        fakeRepository = FakeRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `get products success`() {
        val dataDummy = DataDummy.generateProducts()
        val response = MutableLiveData<List<Product>>()
        response.value = dataDummy

//        testCoroutineRule.runBlockingTest {
//            Mockito.doReturn(response)
//                .`when`(localDataSource)
//                .getProducts()
//
////            viewModel = TransactionViewModel(repositoryImpl)
//            fakeRepository.getProducts()
//
//            val products = viewModel.products.value
//            Mockito.verify(localDataSource, Mockito.times(1)).getProducts()
//
//            assertEquals(dataDummy.size, products?.size)
//            assertNotNull(products)
//
//            viewModel.products.observeForever(observerProduct)
//            Mockito.verify(observerProduct).onChanged(dataDummy)
//            viewModel.products.removeObserver(observerProduct)
//        }
    }
}