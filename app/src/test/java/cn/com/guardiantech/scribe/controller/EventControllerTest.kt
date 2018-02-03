package cn.com.guardiantech.scribe.controller

import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.database.entity.ActivityEvent
import com.j256.ormlite.dao.Dao
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doAnswer
import org.mockito.junit.MockitoJUnitRunner
import junit.framework.Assert.assertTrue

/**
 * Created by dedztbh on 18-2-2.
 */
@RunWith(MockitoJUnitRunner::class)
class EventControllerTest {

    @Mock
    lateinit var eventDao: Dao<ActivityEvent, String>

    @Test
    fun syncEventListTest() {
        val ea = ActivityEvent("a")
        val eb = ActivityEvent("b")
        val ebn = ActivityEvent("b", eventName = "aaa")
        val ec = ActivityEvent("c")
        //Remote Events: ebn, ec
        val database = hashSetOf(ea, eb)

        doAnswer { invocation ->
            val event = invocation.arguments[0] as ActivityEvent
            database.firstOrNull { it.eventId == event.eventId }?.let { database.remove(it) }
            database.add(event)
            null
        }
                .whenever(eventDao).createOrUpdate(any())

        doAnswer { invocation ->
            val ids = invocation.arguments[0] as Collection<*>
            ids.forEach {
                val removeId = it as String
                database.firstOrNull { it.eventId == removeId }?.let { database.remove(it) }
            }
            null
        }
                .whenever(eventDao).deleteIds(any())

        whenever(eventDao.queryForAll()).thenReturn(database.toList())

        EventController.eventDao = eventDao

        EventController.syncEventListImpl(true, listOf(ebn, ec))

        println(Global.mapper.writeValueAsString(database))
        assertTrue(database.containsAll(setOf(ebn, ec)))
        assertTrue(!database.contains(ea))
        assertTrue(!database.contains(eb))
    }
}