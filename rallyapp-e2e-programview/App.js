Ext
		.define(
				'CustomApp',
				{
					extend : 'Rally.app.App',
					componentCls : 'app',
					items : [ {
						xtype : 'container',
						itemId : 'filter'

					},
					{
						xtype : 'container',
						itemId : 'main'
					} ],
					layout : {
						type : 'vbox',
						align : 'stretch'
					},
					launch : function() {
						// Write app code here
						console.log('Start of lunch function');
						this._loadData(true);
						// API Docs: https://help.rallydev.com/apps/2.1/doc/
					},
					// load the data
					_loadData : function(loadUi) {
						console.log('load data started');
						Ext.create('Rally.data.wsapi.Store', {
							model : 'User Story',
							autoLoad : true,
							listeners : {
								load : function(myStore, data, success) {
									console.log('loaded data', myStore, data,
											success);
									var story = data[0];
									if(!story) console.log("Empty data");
									story.getCollection('Changesets').load({
									    fetch: ['Uri', 'Name', 'CommitTimestamp', 'Message'],
									    sorters: [
									              {
									            	  property: 'CommitTimestamp',
									            	  direction: 'DSC'
									              }
									           ],
									    autoLoad: true,
									    scope: this,
									    callback: function(records, operation, success) {
									        console.log('changesset loaded', records, operation, success);
									        this._updateChangeset(records);
									    }
									});
									// process data
									return this._processData(myStore, data, loadUi);
								},
							scope : this
							},
							fetch : [ 'FormattedID', 'Name', 'ScheduleState',
									'Changesets', 'Defects', 'TestRun', 'LastBuild', 'PassingTestCaseCount', 'TestCaseCount', 'Iteration', 'Release' ]
						});
					},

					// process data
					_processData : function(myStore, data, loadUi) {
						// do the processing

						console.log("loaded data before processing", data);
	
						
						var story = data[0];
			            var defects = story.get('Changesets');
			            
			            Ext.Array.each(defects, function(defect) {
			                //each defect is a plain old javascript object
			                console.log(defect.Message + ' - ' +
			                    defect.CommitTimestamp);
			            });
						var records = _.map(data, function(record) {
							return Ext.apply({
								DefectDetails : {'DefectCount':record.get('Defects').Count, 'USOBID':record.getId()},
								Quality : {'QualityGate': 'Passed','LOC': '4.2K', 'Duplications':'11.3%'},
								ChangeSet:{Uri: "https://github3.cisco.com/as-ci-user-gen/spade-cicd-portal",Message: "No commits yet...",Author: "jiherage",loaded: false},
								TestRun : {'Total_Test_Case': record.get('TestCaseCount'),'Passed_Test_Case': record.get('PassingTestCaseCount')},
							    IterationRef:record.get('Iteration') !==null?record.get('Iteration')._ref:null,
							    ReleaseRef:record.get('Release') !==null?record.get('Release')._ref:null,
							    USRef: record.get('_ref')
							}, record.getData());
						});

						var customData = Ext.create('Rally.data.custom.Store',
								{
									data : records
								});

						//console.log("custom data",records);
						if(loadUi){
							// now create GUI
							this._loadGrid(customData);
						}
						
						// return the updated data
						return customData;

					},

					_updateChangeset: function(data){
						console.log('updatechangeset', data[0].raw.Uri, this.myGrid.store.data.map);
						
						//var map = this.myGrid.store.data.map;
//						map.each(function(key, value, length){
//						    console.log(key, value, length);
//						});

						this.myGrid.refresh();
						

					},
					
					_loadGrid : function(myStore) {

						this.myGrid = Ext
								.create(
										'Rally.ui.grid.Grid',
										{
											store : myStore,
											margin: '5 0 0 5',
											style: {bodyColor:'#000000', borderStyle:'solid', borderWidth:'1px', background:'#efcca0'},
											bodyStyle: {
											    background: '#ffc',
											    padding: '10px'
											},
					                        plugins: [{
					                             ptype: 'rowexpander',
					                             rowBodyTpl : new Ext.XTemplate(
					                             '<p><b>SCM:</b> {SCM}</p>',
					                             '<p><b>CI Server:</b> {Ci_Server}</p><br>',
					                             '<p><b>Quality Server:</b> {QAServer}</p>',
					                                {
					                                  formatChange: function(v){
					                                    var color = v >= 0 ? 'green' : 'red';
					                                    return '<span style="color: ' + color + ';">' + v + '</span>';
					                                }
					                             })
					                        }],
											columnCfgs : [
													{
														xtype : 'templatecolumn',
														text : 'ID',
														dataIndex : 'FormattedID',
														width : 50,
														//align : 'center',
														tpl : Ext
																.create('Rally.ui.renderer.template.FormattedIDTemplate')
													},
													{

														text : 'Name',
														dataIndex : 'Name',
														width : 180,
														//align : 'center',
														flex : 0
													},
													{

														text : 'Schedule State',
														dataIndex : 'ScheduleState'
													},
													{

														text : 'Latest Commit',
														dataIndex : 'Changesets',
														width : 180,
														//align : 'center',
														renderer : function(
																value) {
															console.log('Changeset Ref', value._ref);
														
															return Ext.String
																	.format(
																			'<div style="padding: 5px; height:120px;color:#000000;border-style:solid;border-width:2px;background:#daedea" onmouseover="this.style.background=\'white\';" onmouseout="this.style.background=\'#daedea\';"><div><b>Checkin URL:</b> <a href="{0}" target="_blank">Github</a> </div><div> <b>Commit Message:</b> {1} </div><br><div> <b>Author:</b> anunag </div> </div>',
																			'https://github3.cisco.com/as-ci-user-gen/spade-cicd-portal/commit/2eb651567629bf05b1c6f9c88b913c657b96365d', 'US39: updated rally custom app for change set display');

														}
													},
													{

														text : 'Lastest Build',
														dataIndex : 'LastBuild',
														width : 180,
														//align : 'center',
														renderer : function(
																value) {
															console.log(value);
															return Ext.String
															.format(
																	'<div style="align: left; padding: 5px; height:120px;color:#000000;border-style:solid;border-width:2px;background:#daedea" onmouseover="this.style.background=\'white\';" onmouseout="this.style.background=\'#daedea\';"><b>Last Build (<a href="{0}" target="_blank">#9</a>) <br> <br><div style="color:green">Last Stable Build (<a href="{1}" target="_blank">#9</a>)</div> <br><div style="color:red">Last Failed Build (<a href="{2}" target="_blank">#7</a>)</div>  </div>',
																	'https://engci-private-sjc.cisco.com/jenkins/sso-as/job/sandbox/job/spade-portal-webservice/9/console', 'https://engci-private-sjc.cisco.com/jenkins/sso-as/job/sandbox/job/spade-portal-webservice/9/console','https://engci-private-sjc.cisco.com/jenkins/sso-as/job/sandbox/job/spade-portal-webservice/7/');
														},
														tpl : Ext
																.create('Rally.ui.renderer.template.CardDefectSummaryTemplate')
													},
													{

														text : 'Lastest Test Run',
re														width : 180,
														//align : 'center',
														tpl : Ext
																.create('Rally.ui.renderer.template.CardTestCaseSummaryTemplate'),
														renderer : function(
																		value) {
															//console.log(value);
															var bkgCol = '#daedea';
															var result = 'Passed';
															if(value.Passed_Test_Case !== value.Total_Test_Case){
																bkgCol ='red';
																result = 'Failed';
															}
															return Ext.String
																	.format(
																			'<div style="padding: 5px; height:120px;color:#000000;border-style:solid;border-width:2px;background:{4}" onmouseover="this.style.background=\'white\';" onmouseout="this.style.background=\'{4}\';"><b>Status: </b>{2}<br> <b>No of Test Executed:</b> {0} <br> <b>No of Test Passed:<b> {1} <br> <b>No of Test Failed:<b> {3} </div>',
																			value.Total_Test_Case, value.Passed_Test_Case,result,(value.Total_Test_Case-value.Passed_Test_Case),bkgCol);
														}
														
													},
													{
														text : 'Latest Artifacts',
														dataIndex : 'Artifacts',
														width : 180,
														//align : 'center',
														renderer : function(
																value) {
															console.log(value);
															return Ext.String
															.format(
																	'<div style="padding: 5px; height:120px;color:#000000;border-style:solid;border-width:2px;background:#daedea" onmouseover="this.style.background=\'white\';" onmouseout="this.style.background=\'#daedea\';"> <b>Last packaging status:</b> {1} <br> <br> <a href="{0}" target="_blank">Artifactory</a> </div>',
																	'http://engci-maven.cisco.com/artifactory/webapp/#/builds/sandbox%20::%20spade-portal-webservice/5/1483423668598/published/','Passed');
												        }
													},
													{

														text : 'Code Quality',
														dataIndex : 'Quality',
														width : 180,
														//align : 'center',
														renderer : function(
																value) {
															var bkgCol = '#daedea';
															
															if(value.QualityGate !== 'Passed'){
																bkgCol ='red';
															}
															return Ext.String
															.format(
																	'<div style="padding: 5px; height:120px;color:#000000;border-style:solid;border-width:2px;background:{2}" onmouseover="this.style.background=\'white\';" onmouseout="this.style.background=\'{2}\';"> <b>Quality gate:</b> {0}, <br><b>Lines of Code:</b> {1}  <br> <b>Duplication:</b> {3} <br><br> <a href="{4}" target="_blank">For details click here</a></div>',
																	value.QualityGate,value.LOC,bkgCol,value.Duplications, 'https://engci-sonar-blr.cisco.com/sonar/overview?id=as-spade-portal-webservice');
												        }
													},
													{

														text : 'Open Defects',
														width : 180,
														//align : 'center',
														dataIndex : 'DefectDetails',
														renderer : function(
																value) {
															var bkgCol = '#daedea';
															
															if(value.DefectCount !== 0){
																bkgCol ='red';
															}
															return Ext.String
															.format(
																	'<div style="padding: 5px; height:120px;color:#000000;border-style:solid;border-width:2px;background:{1}" onmouseover="this.style.background=\'white\';" onmouseout="this.style.background=\'{1}\';">Open Defects: {0}<br> <br> <a href="{2}" target="_blank">Click Here for Details</a></div>',value.DefectCount,bkgCol,'https://rally1.rallydev.com/#/60911267918d/detail/userstory/'+value.USOBID+'/defects');
												        }
													} ]

										});

						this.UserStoryState = {
								 	xtype : 'rallyfieldvaluecombobox',
								 	flex : 0,
								 	padding: 5,
								 	model: 'UserStory',
							        field: 'ScheduleState',
								 	style : {
								 		borderColor:'#000000', borderStyle:'solid', borderWidth:'1px'
								 		},
								 		listeners: {
											select: function(combobox, records){
						                        console.log(combobox, records);
												var store = this.myGrid.getStore();
						                        store.clearFilter(true);
						                        store.filter({							           
										        	   property: 'ScheduleState',
										        	   operator: '=',
										        	   value: this.down('rallyfieldvaluecombobox').getValue(),
										        	   root: 'data'
										           });
						                        this.myGrid.refresh();
											},
											scope: this
										}
						     	};
						
						this.relaseCombo = {
								xtype : 'rallyreleasecombobox',
								padding: 5,
								style : {
									borderColor:'#000000', borderStyle:'solid', borderWidth:'1px', marginLef:'10px'
								},
								listeners: {
									select: function(combobox, records){
				                        console.log(combobox, records);
										var store = this.myGrid.getStore();
				                        store.clearFilter(true);
				                        store.filter({							           
								        	   property: 'ReleaseRef',
								        	   operator: '=',
								        	   value: this.down('rallyreleasecombobox').getValue(),
								        	   root: 'data'
								           });
				                        this.myGrid.refresh();
									},
									scope: this
								}
							};
						
						this.iteCombo = {
								xtype : 'rallyiterationcombobox',
								padding: 5,
								style : {
									borderColor:'#000000', borderStyle:'solid', borderWidth:'1px', marginLef:'10px'
								},
								listeners: {
									select: function(combobox, records){
										console.log('selected',combobox, records);
				                        var store = this.myGrid.getStore();
				            
				                        store.clearFilter(true);
				                        store.filter({							           
								        	   property: 'IterationRef',
								        	   operator: '=',
								        	   value: this.down('rallyiterationcombobox').getValue(),
								        	   root: 'data'
								           });
				                        this.myGrid.refresh();
									},
									scope: this
								}
							};
						
						this.artifactSearchCombo = {
								xtype : 'rallyartifactsearchcombobox',
								padding: 5,
								storeConfig: {
						            models: ['userstory']
						        },
								style : {
									borderColor:'#000000', borderStyle:'solid', borderWidth:'1px', marginLef:'10px'
								},
								sorters: [
							              {
							            	  property: 'FormattedID',
							            	  direction: 'ASC'
							              }
							           ],
								listeners: {
									select: function(combobox, records){
										console.log('selected',combobox, records[0].data._ref);
				                        var store = this.myGrid.getStore();
				            
				                        store.clearFilter(true);
				                        store.filter({							           
								        	   property: 'USRef',
								        	   operator: '=',
								        	   value: this.down('rallyartifactsearchcombobox').getValue(),
								        	   root: 'data'
								           });
				                        this.myGrid.refresh();
									},
									scope: this
								}
							};
						
						var filterContainer = Ext.create('Ext.container.Container', {
						    layout: {
						        type: 'hbox',
						        align : 'stretch'
						    },
						    frame: true,
						    margin: '5 0 0 5',
						    height: '50px',
						    title: 'Filters',
						    //renderTo: Ext.getBody(),
						    //border: 1,
						    style: {bodyColor:'#000000', borderStyle:'solid', borderWidth:'1px', background:'#ede3d7'},
						    items: [this.relaseCombo,this.iteCombo,this.UserStoryState,this.artifactSearchCombo]
						});


						
						

						this.down('#filter').add(filterContainer);


						this.down('#main').add(this.myGrid);

					}
				});
