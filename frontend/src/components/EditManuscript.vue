<template>

    <v-card outlined>
        <v-card-title>
            EditManuscript
        </v-card-title>

        <v-card-text>
            <Number label="Id" v-model="value.id" :editMode="editMode"/>
            <String label="MansuscriptTitle" v-model="value.mansuscriptTitle" :editMode="editMode"/>
            <String label="ManiscriptContent" v-model="value.maniscriptContent" :editMode="editMode"/>
            <Number label="AuthorId" v-model="value.authorId" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="editManuscript"
            >
                EditManuscript
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>

export default {
    name: 'EditManuscriptCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.id = 0;
        this.value.mansuscriptTitle = '';
        this.value.maniscriptContent = '';
        this.value.authorId = 0;
    },
    watch: {
    },
    methods: {
        editManuscript() {
            this.$emit('editManuscript', this.value);
        },
        close() {
            this.$emit('closeDialog');
        },
        change() {
            this.$emit("update:modelValue", this.value);
        },
    }
}
</script>

