package id.pamoyanan_dev.l_extras.util.job

import android.app.PendingIntent
import android.content.Context
import android.util.Log
import com.firebase.jobdispatcher.*
import com.google.gson.Gson
import id.pamoyanan_dev.l_extras.util.AlarmManagerUtil
import id.pamoyanan_dev.l_extras.util.Injection
import id.pamoyanan_dev.l_extras.util.receiver.AdzanReceiver
import id.pamoyanan_dev.l_extras.util.receiver.AdzanReceiver.Companion.ADZAN_DATA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MySchedulerJob : JobService() {

    override fun onStartJob(job: JobParameters): Boolean {
        when (job.tag) {
            SIMPLE_JOB_TAG -> setupAlarm()
            else -> return false
        }
        return true
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        setupAlarm().cancel()
        return true
    }

    private fun setupAlarm() = GlobalScope.launch(Dispatchers.IO) {
        val repository = Injection.provideGitsRepository(application)
        val adzansList = repository.getAllJadwalSholat()
        adzansList.let {
            it.forEach { item ->
                AlarmManagerUtil.setAlarm(
                    context = applicationContext,
                    alarmId = item.id,
                    originalTime = item.originalTime,
                    flags = PendingIntent.FLAG_UPDATE_CURRENT,
                    receiverClass = AdzanReceiver::class.java
                ) {
                    val adzanDataAsString = Gson().toJson(item)
                    putExtra(ADZAN_DATA, adzanDataAsString)
                }
            }
        }
    }


    private fun simpleJob(job: JobParameters) {
        Log.d("JobScheduler", "Ran job ${job.tag}")
        jobFinished(job, false)
    }

    companion object {
        private const val SIMPLE_JOB_TAG = "uk.co.jakelee.scheduledjobs.job"

        fun scheduleJob(context: Context) {
            val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
            val exampleJob = dispatcher.newJobBuilder()
                .setService(MySchedulerJob::class.java)
                .setTag(SIMPLE_JOB_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK, Constraint.DEVICE_CHARGING)
                .setTrigger(Trigger.executionWindow(0, 3600))
            dispatcher.mustSchedule(exampleJob.build())
        }

        fun cancelJobs(context: Context) {
            FirebaseJobDispatcher(GooglePlayDriver(context)).cancelAll()
        }
    }

}