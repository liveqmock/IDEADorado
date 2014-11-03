package com.bstek.designer.core.config;

import com.bstek.dorado.common.ClientType;
import com.bstek.dorado.idesupport.model.Child;
import com.bstek.dorado.idesupport.model.ClientEvent;
import com.bstek.dorado.idesupport.model.Property;
import com.bstek.dorado.idesupport.model.Rule;
import com.intellij.designer.model.MetaModel;
import com.intellij.openapi.project.Project;

import java.util.*;

/**
 * 模型引用附加信息定义
 *
 * @author Robin
 */
public class DoradoConfigRulesModelMeta {

    protected Map<String, DoradoAttributeMeta> attributes = new HashMap<String, DoradoAttributeMeta>();

    protected Map<String, DoradoReferenceMeta> references = new HashMap<String, DoradoReferenceMeta>();

    protected Rule rule;

    protected MetaModel metaModel;


    //	protected IAdaptable adapter;

    protected final static String DOT_SETTINGS_FOLDER = ".settings";

    public DoradoConfigRulesModelMeta(Rule rule) {
        this.rule = rule;
    }

    public DoradoConfigRulesModelMeta(Project project, Rule rule) {
        this(rule);
//		try {
//			this.adapter = adapter;
//			if (adapter instanceof IProject) {
//				IFile dotSettingFolderFile = ((IProject) adapter)
//						.getFile(DOT_SETTINGS_FOLDER);
//				if (dotSettingFolderFile.getLocation().toFile().exists()
//						&& getIcon() != null) {
//					IPath path = dotSettingFolderFile.getLocation().append(
//							getIcon());
//					if (path != null) {
//						File iconFile = path.toFile();
//						if (iconFile.exists()) {
//							URL url;
//							url = iconFile.toURI().toURL();
//							ImageRegistry imageRegistry = DoradoImages
//									.getImageRegistry();
//							if (imageRegistry != null) {
//								ImageDescriptor desc = ImageDescriptor
//										.createFromURL(url);
//								String icon = getIcon();
//								Image image = imageRegistry.get(icon);
//								if (image != null) {
//									imageRegistry.remove(icon);
//								}
//								imageRegistry.put(icon, desc);
//							}
//						}
//					}
//				}
//			}
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
    }

    public DoradoAttributeMeta getAttributeMeta(String name) {
        return (DoradoAttributeMeta) this.attributes.get(name);
    }

    public void addAttributeMeta(DoradoAttributeMeta meta) {
        this.attributes.put(meta.getName(), meta);
    }

    public void addReferenceMeta(DoradoReferenceMeta meta) {
        this.references.put(meta.getName(), meta);
    }

    public DoradoReferenceMeta getReferenceMeta(String name) {
        return (DoradoReferenceMeta) this.references.get(name);
    }

    public Map<String, Property> getPropertyMap() {
        return rule.getProperties();
    }

    public Property getPrimitiveProperty(String name) {
        return rule.getPrimitiveProperty(name);
    }

    public Map<String,Property> getPrimitivePropertyMap(){
        return rule.getPrimitiveProperties();
    }

    public Map<String, ClientEvent> getClientEvents() {
        return rule.getClientEvents();

    }

    public String getCategory() {
        return rule.getCategory();
    }

    public String getParents() {
        Rule[] rules = rule.getParents();
        return rules[0].getName();
    }

    public String getNodeName() {
        return rule.getNodeName();
    }

    public Rule getRule() {
        return rule;
    }

    public int getSortFactor() {
        return rule.getSortFactor();
    }

    public Map<String, Child> getChildren() {
        return rule.getChildren();
    }

    public Map<String, Child> getSubChildren() {
        LinkedHashMap<String, Child> childrenMap = new LinkedHashMap<String, Child>();
        for (Iterator<Child> iter = getChildren().values().iterator(); iter
                .hasNext(); ) {
            Child child = (Child) iter.next();
            boolean isAggregated = child.isAggregated();
            boolean isFixed = child.isFixed();
            String reserve = child.getReserve();
            getSubChildren(child, childrenMap, isAggregated, isFixed, reserve);
        }
        return childrenMap;
    }

    private void getSubChildren(Child child,
                                LinkedHashMap<String, Child> childrenMap, boolean isAggregated,
                                boolean isFixed, String reserve) {
        try {
            Set<Rule> subChildrenRules = child.getConcreteRules();
            for (Iterator<Rule> iter = subChildrenRules.iterator(); iter
                    .hasNext(); ) {
                Rule subChildRule = (Rule) iter.next();
                String childName = subChildRule.getName();
                Child subChild = new Child(childName);
                subChild.setAggregated(isAggregated);
                subChild.setFixed(isFixed);
                subChild.setReserve(reserve);
                subChild.setRule(subChildRule);
                subChild.setUserData(child);
                childrenMap.put(childName, subChild);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLabel() {
        return rule.getLabel();
    }

    public boolean isDeprecated() {
        return rule.isDeprecated();
    }

    public boolean supportTouch() {
        return ClientType.supportsTouch(getClientTypes());
    }

    public boolean supportDeskTop() {
        return ClientType.supportsDesktop(getClientTypes());
    }

    public int getClientTypes() {
        return rule.getClientTypes();
    }

    public String getIcon() {
        return rule.getIcon();
    }

    public void setMetaModel(MetaModel metaModel){
        this.metaModel=metaModel;
    }

    public MetaModel getMetaModel() {
        return metaModel;
    }

//	public Image getImage() {
//		return DoradoImages.getRuleImage(getIcon());
//	}
//
//	public ImageDescriptor getImageDescriptor() {
//		return DoradoImages.getRuleImageDescriptor(getIcon());
//	}
//
//	public Image getDeprecatedImage() {
//		Image image = getImage();
//		image = new Image(null, image, SWT.IMAGE_GRAY);
//		return image;
//	}
//
//	public ImageDescriptor getDeprecatedImageDescriptor() {
//		ImageDescriptor imagedesc = getImageDescriptor();
//		Image image = new Image(null, imagedesc.createImage(), SWT.IMAGE_GRAY);
//		imagedesc = ImageDescriptor.createFromImage(image);
//		return imagedesc;
//	}


}