define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/i18n",
    "dojo/i18n!./nls/hpcc",
    "dojo/dom",
    "dojo/dom-attr",

    "dijit/registry",

    "dgrid/OnDemandGrid",
    "dgrid/Keyboard",
    "dgrid/Selection",
    "dgrid/selector",
    "dgrid/extensions/ColumnResizer",
    "dgrid/extensions/DijitRegistry",

    "hpcc/_TabContainerWidget",
    "src/ws_access",
    "hpcc/DelayLoadWidget",

    "dojo/text!../templates/GroupDetailsWidget.html",

    "dijit/form/Textarea",
    "dijit/form/TextBox",
    "dijit/form/Button",
    "dijit/form/ToggleButton",
    "dijit/Toolbar",
    "dijit/TooltipDialog",
    "dijit/TitlePane",
    "dijit/Dialog"

], function (declare, lang, i18n, nlsHPCC, dom, domAttr,
    registry,
    OnDemandGrid, Keyboard, Selection, selector, ColumnResizer, DijitRegistry,
    _TabContainerWidget, WsAccess, DelayLoadWidget,
    template) {
        return declare("GroupDetailsWidget", [_TabContainerWidget], {
            templateString: template,
            baseClass: "GroupDetailsWidget",
            i18n: nlsHPCC,

            summaryWidget: null,
            membersWidget: null,
            activePermissionsWidget: null,
            groupPermissionsWidget: null,

            postCreate: function (args) {
                this.inherited(arguments);
                this.summaryWidget = registry.byId(this.id + "_Summary");
                this.membersWidget = registry.byId(this.id + "_Members");
                this.activePermissionsWidget = registry.byId(this.id + "_ActivePermissions");
                this.groupPermissionsWidget = registry.byId(this.id + "_GroupPermissions");
            },

            getTitle: function () {
                return this.i18n.GroupDetails;
            },

            //  Hitched actions  ---
            _onSave: function (event) {
                //  Currently disabled.  TODO:  Add ESP Method to rename group?  ---
            },

            //  Implementation  ---
            init: function (params) {
                if (this.inherited(arguments))
                    return;

                this.group = params.Name;
                if (this.group) {
                    this.updateInput("Group", null, this.group);
                    this.updateInput("Name", null, this.group);
                }
            },

            initTab: function () {
                var currSel = this.getSelectedChild();

                if (currSel.id === this.membersWidget.id) {
                    this.membersWidget.init({
                        groupname: this.group
                    });
                } else if (currSel.id === this.activePermissionsWidget.id) {
                    this.activePermissionsWidget.init({
                        IsGroup: true,
                        IncludeGroup: false,
                        AccountName: this.group
                    });
                } else if (currSel.id === this.groupPermissionsWidget.id) {
                    this.groupPermissionsWidget.init({
                        IsGroup: true,
                        IncludeGroup: false,
                        groupname: this.group
                    });
                }
            }
        });
    });