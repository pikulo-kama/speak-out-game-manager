package com.drabazha.speakout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.drabazha.speakout.R
import com.drabazha.speakout.model.Team
import com.drabazha.speakout.view.TeamListView

class TeamListAdapter(context: Context, var expandableListView: TeamListView,
                      private var teams: ArrayList<Team>) : BaseExpandableListAdapter() {

    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getGroupCount(): Int {
        return teams.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return teams[groupPosition].players.size
    }

    override fun getGroup(groupPosition: Int): String {
        return teams[groupPosition].teamName
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String {
        return teams[groupPosition].players[childPosition].playerName
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val converterViewCopy = convertView ?: inflater.inflate(R.layout.layout_team_list_group, null)
        val title = converterViewCopy?.findViewById<TextView>(R.id.tvTeamName)
        title?.setBackgroundColor(teams[groupPosition].teamColor)
        title?.text = "${teams[groupPosition].emoji} ${getGroup(groupPosition).uppercase()}"
        title?.setOnClickListener {
            if (expandableListView.isGroupExpanded(groupPosition)) {
                expandableListView.collapseGroup(groupPosition)
            } else {
                expandableListView.expandGroup(groupPosition)
            }
        }
        return converterViewCopy
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val converterViewLocal = convertView ?: inflater.inflate(R.layout.layout_team_list_child, null)
        val title = converterViewLocal?.findViewById<TextView>(R.id.tvTeamName)
        title?.text = "${"✔️"} ${getChild(groupPosition, childPosition)}"
        return converterViewLocal
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}