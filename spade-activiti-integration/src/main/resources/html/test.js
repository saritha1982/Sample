Ext.create('Rally.data.wsapi.TreeStoreBuilder').build({
      models: ['userstory'],
      autoLoad: true,
      enableHierarchy: true
  }).then({
      success: function(store) {
          Ext.create('Ext.Container', {
              items: [{
                  xtype: 'rallygridboard',
                  context: this.getContext(),
                  modelNames: ['userstory'],
                  toggleState: 'grid',
                  plugins: [
                      'rallygridboardtoggleable'
                  ],
                  cardBoardConfig: {
                      attribute: 'ScheduleState'
                  },
                  gridConfig: {
                      store: store,
                      columnCfgs: [
                          'Name',
                          'ScheduleState',
                          'Owner',
                          'PlanEstimate'
                      ]
                  },
                  height: this.getHeight()
              }]
          });
      }
  });