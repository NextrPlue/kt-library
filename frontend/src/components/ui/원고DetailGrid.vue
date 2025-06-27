<template>
    <div class="panel">
        <div class="label-title">{{label}}</div>
        <div class="table-responsive">
            <v-btn v-if="editMode" @click="addDetailRow()">추가</v-btn>
            <v-btn v-if="editMode" style="margin-left: 3px;" @click="detailDeleteRow()">삭제</v-btn>
            <v-table v-if="!editMode">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>authorId</th>
                        <th>createdAt</th>
                        <th>updatedAt</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(detailVal, idx) in selectedRow.원고s" :key="detailVal" >
                        <td class="font-semibold">{{ idx + 1 }}</td>
                        <td class="whitespace-nowrap">{{ detailVal.authorId }}</td>
                        <td class="whitespace-nowrap">{{ detailVal.createdAt }}</td>
                        <td class="whitespace-nowrap">{{ detailVal.updatedAt }}</td>
                        <td class="whitespace-nowrap">
                            <Icon icon="mi:delete" @click="deleteRow(detailVal)" />
                        </td>
                    </tr>
                </tbody>
            </v-table>
            <v-table v-else>
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>authorId</th>
                        <th>createdAt</th>
                        <th>updatedAt</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(addVal, idx) in newValue" :key="addVal" >
                        <td class="font-semibold">{{ idx + 1 }}</td>
                        <td class="whitespace-nowrap">
                            <Number style="margin-left: -5px; width: 150px;" :editMode="editMode" v-model="addVal.authorId"/>
                        </td>
                        <td class="whitespace-nowrap">
                            <Date style="margin-left: -5px; width: 150px;" :editMode="editMode" v-model="addVal.createdAt"/>
                        </td>
                        <td class="whitespace-nowrap">
                            <Date style="margin-left: -5px; width: 150px;" :editMode="editMode" v-model="addVal.updatedAt"/>
                        </td>
                    </tr>
                </tbody>
            </v-table>
        </div>
    </div>
</template>

<script>
import BaseDetailGrid from '../base-ui/BaseDetailGrid.vue';

export default {
    name: '원고',
    mixins: [BaseDetailGrid],
    components: {
    },
    props: {
        label: String,
        editMode: Boolean,
        selectedRow: Object,
    },
    data: ()=>({
    }),
    created(){
    },
    methods: {
    }
}
</script>
