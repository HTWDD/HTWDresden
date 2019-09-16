package de.htwdd.htwdresden.ui.models

import androidx.databinding.ObservableField
import de.htwdd.htwdresden.BR
import de.htwdd.htwdresden.R
import de.htwdd.htwdresden.adapter.ExamBindables
import de.htwdd.htwdresden.interfaces.Identifiable
import de.htwdd.htwdresden.utils.extensions.defaultWhenNull
import de.htwdd.htwdresden.utils.holders.StringHolder
import java.io.Serializable

interface Examable: Identifiable<ExamBindables>
interface ExamableModels

// region - JSON
data class JExam(
    val title: String,
    val examType: String,
    val studyBranch: String,
    val day: String,
    val startTime: String,
    val endTime: String,
    val examiner: String,
    val nextChance: String,
    val rooms: List<String>
): Serializable
// endregion

// region - Model
class Exam(
    val title: String,
    val examType: String,
    val studyBranch: String,
    val day: String,
    val startTime: String,
    val endTime: String,
    val examiner: String,
    val nextChance: String,
    val rooms: List<String>) {

    companion object {
        fun from(json: JExam): Exam {
            return Exam(
                json.title,
                json.examType,
                json.studyBranch,
                json.day,
                json.startTime,
                json.endTime,
                json.examiner,
                json.nextChance,
                json.rooms
            )
        }
    }

}
// endregion

// region - ExamItem
class ExamItem(private val item: Exam): Examable, Comparable<ExamItem> {

    private val bindingTypes: ExamBindables by lazy {
        ExamBindables().apply {
            add(Pair(BR.examModel, examModel))
        }
    }
    private val examModel = ExamModel()
    private val sh: StringHolder by lazy { StringHolder.instance }

    init {
        examModel.apply {
            title.set(item.title)
            examType.set(item.examType
                .replace("SP", sh.getString(R.string.exams_type_written))
                .replace("MP", sh.getString(R.string.exams_type_oral)))
            studyBranch.set(sh.getString(R.string.exams_branch, item.studyBranch.defaultWhenNull("-")))
            day.set(item.day)
            examTime.set("${item.startTime} - ${item.endTime}")
            examiner.set(sh.getString(R.string.exams_examinier, item.examiner))
            nextChance.set(item.nextChance)
            rooms.set(item.rooms.joinToString(", "))
        }
    }

    override fun itemViewType() = R.layout.list_item_examable_exam_bindable

    override fun bindingTypes() = bindingTypes

    override fun compareTo(other: ExamItem) = item.day.compareTo(other.item.day)

    override fun equals(other: Any?) = hashCode() == other.hashCode()

    override fun hashCode() = 31 * item.day.hashCode() + item.title.hashCode()
}
// endregion

// region - ExamModel
class ExamModel: ExamableModels {
    val title       = ObservableField<String>()
    val examType    = ObservableField<String>()
    val studyBranch = ObservableField<String>()
    val day         = ObservableField<String>()
    val examTime    = ObservableField<String>()
    val examiner    = ObservableField<String>()
    val nextChance  = ObservableField<String>()
    val rooms       = ObservableField<String>()
}
// endregion